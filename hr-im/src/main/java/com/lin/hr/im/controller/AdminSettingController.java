package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.config.AppConfig;
import com.lin.hr.common.constants.AccountConstant;
import com.lin.hr.common.constants.FileConstant;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.im.entity.query.GroupInfoQuery;
import com.lin.hr.im.mappers.GroupInfoMapper;
import com.lin.hr.im.service.GroupInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/13 17:01
 **/
@RestController
@RequestMapping("/admin")
public class AdminSettingController extends ABaseController {
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private AppConfig appConfig;

    @PostMapping("/getSysSetting")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO<Object> getSysSetting() {
        return getSuccessResponseVO(redisComponent.getSysSetting());
    }

    @PostMapping("/saveSysSetting")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO<Object> saveSysSetting(SysSettingDto sysSettingDto, MultipartFile robotFile, MultipartFile robotCover) {
        if (robotFile != null) {
            String baseFolder = appConfig.getProjectFolder() + FileConstant.FILE_FOLDER_FILE;
            File targetFileFolder = new File(baseFolder + FileConstant.FILE_FOLDER_AVATAR_NAME);
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            String filePath = targetFileFolder.getPath() + "/" + AccountConstant.ROBOT_UID + FileConstant.IMAGE_SUFFIX;
            try {
                robotFile.transferTo(new File(filePath));
                robotCover.transferTo(new File(filePath + FileConstant.COVER_IMAGE_SUFFIX));
            } catch (IOException e) {
                throw new BusinessException("机器人头像图片上传失败！");
            }
        }
        redisComponent.saveSysSetting(sysSettingDto);
        return getSuccessResponseVO("设置系统配置成功！");
    }
}
