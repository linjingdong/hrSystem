package com.lin.hr.common.enums.user;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/22 21:10
 **/
@Getter
public enum UserContactTypeEnum {
    USER(0, "U", "好友"),
    GROUP(1, "G", "群");


    private final Integer type;
    private final String prefix;
    private final String desc;

    UserContactTypeEnum(Integer type, String prefix, String desc) {
        this.type = type;
        this.prefix = prefix;
        this.desc = desc;
    }

    public static UserContactTypeEnum getUserContactTypeEnum(String name) {
        try {
            if (StringUtils.isEmpty(name)) {
                return null;
            } else {
                return UserContactTypeEnum.valueOf(name);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static UserContactTypeEnum getByType(String type) {
        for (UserContactTypeEnum userContactType : UserContactTypeEnum.values()) {
            if (userContactType.getType().equals(Integer.parseInt(type))) {
                return userContactType;
            }
        }
        return null;
    }

    public static UserContactTypeEnum getByPrefix(String prefix) {
        try {
            if (StringUtils.isEmpty(prefix) || prefix.trim().isEmpty()) {
                return null;
            }
            prefix = prefix.substring(0, 1);
            for (UserContactTypeEnum type : UserContactTypeEnum.values()) {
                if (type.getPrefix().equals(prefix)) {
                    return type;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
