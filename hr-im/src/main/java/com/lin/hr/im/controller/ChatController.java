package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.config.AppConfig;
import com.lin.hr.common.constants.FileConstant;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.ChatMessage;
import com.lin.hr.im.service.ChatMessageService;
import com.lin.hr.im.service.ChatSessionUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/15 00:25
 **/
@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController extends ABaseController {
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private ChatSessionUserService chatSessionUserService;
    @Resource
    private AppConfig appConfig;

    @GlobalInterceptor
    @PostMapping("/sendMessage")
    public ResponseVO<Object> sendMessage(@NotEmpty String contactId,
                                          @NotEmpty @Max(500) String messageContent,
                                          @NotNull Integer messageType,
                                          Long fileSize,
                                          String fileName,
                                          Integer fileType) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        // 保存消息
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContactId(contactId);
        chatMessage.setMessageContent(messageContent);
        chatMessage.setFileSize(fileSize);
        chatMessage.setFileName(fileName);
        chatMessage.setFileType(fileType);
        chatMessage.setMessageType(messageType);
        MessageSendDto<Object> messageSendDto = chatMessageService.saveMessage(chatMessage, tokenUserInfo);
        return getSuccessResponseVO(messageSendDto);
    }

    @GlobalInterceptor
    @PostMapping("/uploadFile")
    public ResponseVO<Object> uploadFile(@NotNull Long messageId, @NotNull MultipartFile file, @NotNull MultipartFile cover) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        chatMessageService.saveMessageFile(tokenUserInfo.getUserId(), messageId, file, cover);
        return getSuccessResponseVO(null);
    }

    @GlobalInterceptor
    @PostMapping("/downloadFile")
    public void downloadFile(HttpServletResponse response, @NotEmpty String fileId, @NotNull Boolean showCover) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        OutputStream out = null;
        FileInputStream in = null;
        try {
            File file = null;
            if (!StringTools.isNumber(fileId)) {
                String avatarFolderName = FileConstant.FILE_FOLDER_FILE + FileConstant.FILE_FOLDER_AVATAR_NAME;
                String avatarPath = appConfig.getProjectFolder() + avatarFolderName + fileId + FileConstant.IMAGE_SUFFIX;
                if (showCover) {
                    avatarPath = avatarPath + FileConstant.COVER_IMAGE_SUFFIX;
                }
                file = new File(avatarPath);
                if (!file.exists()) {
                    throw new BusinessException(ResponseCodeEnum.CODE_602);
                }
            } else {
                chatMessageService.downloadFile(tokenUserInfo, Long.parseLong(fileId), showCover);
            }
            response.setContentType("application/x-msdownload; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;");
            response.setContentLengthLong(file.length());
            in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            log.error("下载文件失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    log.error("IO异常", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    log.error("IO异常", e);
                }
            }
        }
    }
}
