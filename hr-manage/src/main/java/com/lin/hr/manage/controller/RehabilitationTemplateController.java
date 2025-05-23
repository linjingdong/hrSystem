package com.lin.hr.manage.controller;

import java.util.List;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.dto.RehabilitationTemplateDto;
import com.lin.hr.manage.entity.query.RehabilitationTemplateQuery;
import com.lin.hr.manage.entity.po.RehabilitationTemplate;
import com.lin.hr.manage.entity.po.RehabilitationTemplateItem;
import com.lin.hr.manage.entity.vo.RehabilitationTemplateVo;
import com.lin.hr.manage.service.RehabilitationTemplateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复训练模板表：后台预设标准训练方案 Controller
 */
@RestController("rehabilitationTemplateController")
@RequestMapping("/rehabilitationTemplate")
public class RehabilitationTemplateController extends ABaseController {

    @Resource
    private RehabilitationTemplateService rehabilitationTemplateService;

    /**
     * 新增
     */
    @RequestMapping("/add")
    public ResponseVO add(RehabilitationTemplate bean) {
        rehabilitationTemplateService.add(bean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增
     */
    @RequestMapping("/addBatch")
    public ResponseVO addBatch(@RequestBody List<RehabilitationTemplate> listBean) {
        rehabilitationTemplateService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增/修改
     */
    @RequestMapping("/addOrUpdateBatch")
    public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationTemplate> listBean) {
        rehabilitationTemplateService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据TemplateId修改对象
     */
    @RequestMapping("/updateRehabilitationTemplateByTemplateId")
    public ResponseVO updateRehabilitationTemplateByTemplateId(RehabilitationTemplate bean, String templateId) {
        rehabilitationTemplateService.updateRehabilitationTemplateByTemplateId(bean, templateId);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据TemplateId删除
     */
    @RequestMapping("/deleteRehabilitationTemplateByTemplateId")
    public ResponseVO deleteRehabilitationTemplateByTemplateId(String templateId) {
        rehabilitationTemplateService.deleteRehabilitationTemplateByTemplateId(templateId);
        return getSuccessResponseVO(null);
    }

    /**
     * 创建模板
     */
    @GlobalInterceptor
    @PostMapping("/createTemplate")
    public ResponseVO<Object> createTemplate(@RequestBody RehabilitationTemplateDto dto) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        rehabilitationTemplateService.createTemplate(dto);
        return getSuccessResponseVO("创建训练计划模板成功");
    }

    /**
     * 删除模板
     */
    @GlobalInterceptor
    @PostMapping("/deleteTemplate")
    public ResponseVO<Object> deleteTemplate(String templateId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        rehabilitationTemplateService.deleteById(templateId);
        return getSuccessResponseVO("删除训练计划模板成功");
    }

    /**
     * 根据条件分页查询
     */
    @RequestMapping("/loadDataList")
    @GlobalInterceptor
    public ResponseVO<Object> loadDataList(RehabilitationTemplateQuery query) {
        return getSuccessResponseVO(rehabilitationTemplateService.findListByPage(query));
    }

    /**
     * 根据id查询模板
     */
    @GlobalInterceptor
    @RequestMapping("/getTemplateById")
    public ResponseVO<Object> getTemplateById(String templateId) {
        RehabilitationTemplateVo vo = rehabilitationTemplateService.getTemplateById(templateId);
        return getSuccessResponseVO(vo);
    }

    /**
     * 根据id修改模板
     */
    @GlobalInterceptor
    @RequestMapping("/updateTemplate")
    public ResponseVO<Object> updateTemplateById(RehabilitationTemplateDto dto, String templateId, String itemsJson) {
        try {
            // 记录接收到的参数
            System.out.println("接收到更新请求参数 - templateId: " + templateId);
            System.out.println("dto基本信息: " + dto);
            System.out.println("接收到的明细项JSON: " + itemsJson);
            
            // 如果有明细项JSON，解析并设置到DTO
            if (itemsJson != null && !itemsJson.isEmpty()) {
                // 这里需要添加JSON解析逻辑
                // 假设有一个工具类可以将JSON转为对象列表
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    // 设置日期格式
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    objectMapper.setDateFormat(dateFormat);
                    // 忽略未知属性
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    
                    // 将JSON解析为列表
                    List<RehabilitationTemplateItem> items = objectMapper.readValue(
                        itemsJson,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, RehabilitationTemplateItem.class)
                    );
                    
                    dto.setRehabilitationTemplateItemList(items);
                    System.out.println("解析得到明细项数量: " + items.size());
                } catch (Exception e) {
                    e.printStackTrace();
                    return getServerErrorResponseVO("解析训练明细项失败: " + e.getMessage());
                }
            }
            
            rehabilitationTemplateService.updateById(templateId, dto);
            return getSuccessResponseVO("更新模板成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getServerErrorResponseVO("更新模板失败: " + e.getMessage());
        }
    }

    /**
     * 根据type_id查询模板
     */
    @GlobalInterceptor
    @PostMapping("/getTemplateByTypeId")
    public ResponseVO<Object> getTemplateByTypeId(String typeId) {
        List<RehabilitationTemplateVo> vos = rehabilitationTemplateService.getTemplateByTypeId(typeId);
        return getSuccessResponseVO(vos);
    }
}