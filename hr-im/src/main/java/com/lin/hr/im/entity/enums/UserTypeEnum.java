package com.lin.hr.im.entity.enums;

import lombok.Getter;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/22 20:46
 **/
@Getter
public enum UserTypeEnum {
    PATIENT(0, "patient"),
    DOCTOR(1, "doctor"),
    ADMIN(2, "admin");

    private final Integer code;
    private final String value;

    UserTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static UserTypeEnum getByCode(Integer code) {
        if (code != null) {
            return null;
        } else {
            for (UserTypeEnum userType : UserTypeEnum.values()) {
                if (userType.getCode().equals(code)) {
                    return userType;
                }
            }
            return null;
        }
    }
}
