package com.lin.hr.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lin.hr.common.constants.AccountConstant;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/24 23:11
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysSettingDto implements Serializable {
    // 最大的群组数
    private Integer maxGroupCount = 5;
    // 群组最大人数
    private Integer maxGroupMemberCount = 500;
    // 图片大小
    private Integer maxImageSize = 2;
    // 视频大小
    private Integer maxVideoSize = 5;
    // 文件大小
    private Integer maxFileSize = 5;
    // 机器人id
    private String robotUId = AccountConstant.ROBOT_UID;
    // 机器人昵称
    private String robotNickName = "康复小助手";
    // 机器人欢迎语
    private String robotWelcome = "欢迎使用JD居家康复系统";
}
