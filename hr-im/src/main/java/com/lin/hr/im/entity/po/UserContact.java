package com.lin.hr.im.entity.po;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 联系人
 */
@Data
public class UserContact implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 联系人ID或者群组ID
     */
    private String contactId;

    /**
     * 联系人类型 0:好友 1:群组
     */
    private Integer contactType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 状态 0:非好友 1:好友 2:已删除好友 3:被好友拉黑 4:已拉黑好友
     */
    private Integer status;

    /**
     * 最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;

    /**
     * 用户名称
     */
    private String contactName;

    /**
     * 用户性别
     */
    private String sex;

    @Override
    public String toString() {
        return "用户ID:" + (userId == null ? "空" : userId) + "，联系人ID或者群组ID:" + (contactId == null ? "空" : contactId) + "，联系人类型 0:好友 1:群组:" + (contactType == null ? "空" : contactType) + "，创建时间:" + (createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，状态 0:非好友 1:好友 2:已删除好友 3:被好友拉黑 4:已拉黑好友:" + (status == null ? "空" : status) + "，最后更新时间:" + (lastUpdateTime == null ? "空" : DateUtil.format(lastUpdateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
    }
}
