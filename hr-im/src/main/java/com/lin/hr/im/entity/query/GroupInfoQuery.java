package com.lin.hr.im.entity.query;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 参数
 */
@Setter
@Getter
public class GroupInfoQuery extends BaseParam {
    /**
     * 群ID
     */
    private String groupId;

    private String groupIdFuzzy;

    /**
     * 群组名
     */
    private String groupName;

    private String groupNameFuzzy;

    /**
     * 群主id
     */
    private String groupOwnerId;

    private String groupOwnerIdFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 群公告
     */
    private String groupNotice;

    private String groupNoticeFuzzy;

    /**
     * 0:直接加入, 1:需要管理员同意加入
     */
    private Integer joinType;

    /**
     * 状态 1:正常 0:解散
     */
    private Integer status;

    /**
     * 是否搜索群主名称
     */
    private boolean queryGroupOwenNickName;

    /**
     * 是否搜索成员数量
     */
    private boolean queryGroupMemberCount;
}
