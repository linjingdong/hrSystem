package com.lin.hr.common.enums.user;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/6 12:58
 **/
@Getter
public enum UserContactStatusEnum {
    NOT_FRIEND(0, "非好友"),
    FRIEND(1, "好友"),
    DEL(2, "已删除好友"),
    DEL_BE(3, "被删除好友"),
    BLACKLIST(4, "已拉黑好友"),
    BLACKLIST_BE(5, "被好友拉黑"),
    BLACKLIST_BE_FIRST(6, "首次被好友拉黑");

    private Integer status;
    private String desc;

    UserContactStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private static UserContactStatusEnum getByStatus(String status) {
        try {
            if (StringUtils.isEmpty(status)) {
                return null;
            }
            return UserContactStatusEnum.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static UserContactStatusEnum getByStatus(Integer status) {
        for (UserContactStatusEnum item : UserContactStatusEnum.values()) {
            if (status.equals(item.status)) {
                return item;
            }
        }
        return null;
    }
}
