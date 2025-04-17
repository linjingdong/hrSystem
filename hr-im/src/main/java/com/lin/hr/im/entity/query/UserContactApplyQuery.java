package com.lin.hr.im.entity.query;


import lombok.Getter;
import lombok.Setter;

/**
 * 联系人申请参数
 */
@Setter
@Getter
public class UserContactApplyQuery extends BaseParam {
    /**
     * 自增ID
     */
    private Integer applyId;

    /**
     * 申请人id
     */
    private String applyUserId;

    private String applyUserIdFuzzy;

    /**
     * 接收人ID
     */
    private String receiveUserId;

    private String receiveUserIdFuzzy;

    /**
     * 联系人类型 0:好友 1:群组
     */
    private Integer contactType;

    /**
     * 联系人群组ID
     */
    private String contactId;

    private String contactIdFuzzy;

    /**
     * 最后申请时间
     */
    private Long lastApplyTime;

    /**
     * 状态 0:待处理 1:已同意 2:已拒绝 3:已忽略
     */
    private Integer status;

    /**
     * 申请信息
     */
    private String applyInfo;

    private String applyInfoFuzzy;

    private Boolean queryContactInfo;
}
