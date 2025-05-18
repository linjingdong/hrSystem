package com.lin.hr.im.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.hr.common.config.AppConfig;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.im.utils.vo.MessageContentVo;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/18 14:17
 **/
@Slf4j
@Component
public class ApiUtils {
    @Autowired
    private AppConfig appConfig;
    
    // 创建一个全局的OkHttpClient实例，配置较长的超时时间
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // 连接超时
            .readTimeout(120, TimeUnit.SECONDS)    // 读取超时
            .writeTimeout(60, TimeUnit.SECONDS)    // 写入超时
            .retryOnConnectionFailure(true)        // 连接失败时自动重试
            .build();

    /**
     * 调用DeepSeek API进行聊天
     *
     * @param messageContentVoList 消息内容列表
     * @return 模型生成的响应文本
     */
    public String dSOpenApiInvoke(List<MessageContentVo> messageContentVoList) {
        // 构建请求参数
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("messages", messageContentVoList);
        contentMap.put("model", "deepseek-chat");
        contentMap.put("frequency_penalty", 0);
        contentMap.put("max_tokens", 2048);
        contentMap.put("presence_penalty", 0);
        Map<String, String> typeMap = new HashMap<>();
        typeMap.put("type", "text");
        contentMap.put("response_format", typeMap);
        contentMap.put("stream", false);
        contentMap.put("temperature", 0.7);  // 略微降低温度以提高响应一致性
        contentMap.put("top_p", 1);
        
        String contentMapStr = JSON.toJSONString(contentMap);
        log.info("发送消息 --> {}", contentMapStr);
        
        // 构建请求
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, contentMapStr);
        Request request = new Request.Builder()
                .url("https://api.deepseek.com/chat/completions")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + appConfig.getDeepSeekToken())
                .build();
        
        // 设置重试次数
        int maxRetries = 3;
        int retryCount = 0;
        
        // 发送请求并处理响应，带重试机制
        while (retryCount < maxRetries) {
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    String errorMsg = response.body() != null ? response.body().string() : "Unknown error";
                    log.error("deepSeek请求失败，状态码: {}, 错误信息: {}", response.code(), errorMsg);
                    
                    if (response.code() >= 500) {
                        // 服务器错误，尝试重试
                        retryCount++;
                        if (retryCount < maxRetries) {
                            log.info("第{}次重试DeepSeek API调用", retryCount);
                            // 指数退避策略
                            try {
                                Thread.sleep(1000 * (long)Math.pow(2, retryCount));
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            continue;
                        }
                    }
                    
                    throw new BusinessException(ResponseCodeEnum.CODE_500);
                }
                
                // 如果响应成功，解析响应内容
                String responseBody = response.body().string();
                log.info("deepSeek返回：--> {}", responseBody);
                
                // 解析响应
                JSONObject jsonObject = JSON.parseObject(responseBody);
                if (jsonObject.containsKey("choices") && jsonObject.getJSONArray("choices").size() > 0) {
                    JSONObject choice = jsonObject.getJSONArray("choices").getJSONObject(0);
                    if (choice.containsKey("message") && choice.getJSONObject("message").containsKey("content")) {
                        String content = choice.getJSONObject("message").getString("content");
                        
                        // 清理内容中的HTML标签
                        content = content.replaceAll("<[^>]*>", "");
                        // 移除列表标记（如1., 2., •, -, * 等）
                        content = content.replaceAll("(?m)^\\s*[\\d*\\-•]+\\.?\\s+", "");
                        
                        // 确保回复不超过数据库字段长度限制 (500字符)
                        final int MAX_MESSAGE_LENGTH = 500;
                        if (content != null && content.length() > MAX_MESSAGE_LENGTH) {
                            log.warn("DeepSeek API回复超出长度限制({})，将进行截断处理", MAX_MESSAGE_LENGTH);
                            // 截断消息，保留前490个字符并添加"..."指示被截断
                            content = content.substring(0, MAX_MESSAGE_LENGTH - 10) + "...";
                        }
                        
                        return content;
                    }
                }
                
                log.error("未能从DeepSeek响应中提取内容: {}", responseBody);
                throw new BusinessException(ResponseCodeEnum.CODE_500);
                
            } catch (IOException e) {
                log.error("deepSeek连接异常 (尝试 {}/{})", retryCount + 1, maxRetries, e);
                retryCount++;
                
                if (retryCount < maxRetries) {
                    log.info("第{}次重试DeepSeek API调用", retryCount);
                    // 指数退避策略
                    try {
                        Thread.sleep(1000 * (long)Math.pow(2, retryCount));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    log.error("DeepSeek API调用失败，已达到最大重试次数", e);
                    throw new BusinessException(ResponseCodeEnum.CODE_500);
                }
            } catch (Exception e) {
                log.error("deepSeek调用过程中发生未知异常", e);
                throw new BusinessException(ResponseCodeEnum.CODE_500);
            }
        }
        
        // 如果所有重试都失败
        log.error("所有DeepSeek API调用重试均失败");
        throw new BusinessException(ResponseCodeEnum.CODE_500);
    }
    
    /**
     * 简化版模拟AI回复，当DeepSeek API调用异常时使用
     * @param message 用户消息
     * @return 模拟的AI回复，保证不超过500字符
     */
    public String getFallbackResponse(String message) {
        log.info("使用备用响应机制回复消息: {}", message);
        String response;
        
        if (message.contains("你好") || message.contains("hello") || message.contains("hi")) {
            response = "您好！我是康复小助手，很高兴为您服务。请问有什么关于居家康复的问题可以帮助您？";
        } else if (message.contains("康复") || message.contains("锻炼") || message.contains("恢复")) {
            response = "居家康复需要制定合理的计划，包括适当的运动、均衡的饮食和充足的休息。建议根据医生的指导进行康复训练，避免过度锻炼导致二次伤害。每天坚持锻炼但不要过度，注意身体感受，循序渐进地增加强度。";
        } else if (message.contains("疼痛") || message.contains("痛")) {
            response = "如果您感到疼痛，建议立即停止当前活动，适当休息并冰敷疼痛部位。通常可以采用热敷或冷敷缓解不适，但持续性疼痛应当及时就医。疼痛期间减少相关部位活动，但不要完全制动，以免肌肉萎缩。";
        } else if (message.contains("饮食") || message.contains("吃") || message.contains("食物")) {
            response = "康复期间饮食应当均衡营养，多摄入蛋白质、维生素和矿物质。减少高油、高盐、高糖食物，多吃新鲜蔬果，保持充足水分摄入。优质蛋白如鱼肉蛋奶有助于组织修复，建议少量多餐，易于消化吸收。";
        } else {
            response = "感谢您的咨询。您的问题已记录，建议您咨询专业医护人员获取更准确的康复指导。康复是一个循序渐进的过程，需要耐心和坚持。如有其他问题，请随时向我咨询，我将尽力为您提供帮助。";
        }
        
        // 清理内容中的HTML标签和列表标记
        response = response.replaceAll("<[^>]*>", "");
        response = response.replaceAll("(?m)^\\s*[\\d*\\-•]+\\.?\\s+", "");
        
        // 最后再次确保不超过限制
        final int MAX_MESSAGE_LENGTH = 500;
        if (response.length() > MAX_MESSAGE_LENGTH) {
            response = response.substring(0, MAX_MESSAGE_LENGTH - 10) + "...";
        }
        
        return response;
    }
}
