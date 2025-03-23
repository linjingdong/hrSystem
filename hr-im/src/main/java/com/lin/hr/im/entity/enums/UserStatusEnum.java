package com.lin.hr.im.entity.enums;

import lombok.Getter;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/22 21:37
 **/
@Getter
public enum UserStatusEnum {
    DISABLED(1, "禁用"),
    ENABLE(2, "启用");

    private final Integer status;
    private final String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static UserStatusEnum getByStatus(Integer status) {
        for (UserStatusEnum e : UserStatusEnum.values()) {
            if (e.getStatus().equals(status)) {
                return e;
            }
        }
        return null;
    }
}
