package com.lin.hr.manage.entity.enums.rehab;

import lombok.Getter;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/20 00:18
 **/
@Getter
public enum RehabType {
    ORTHOPEDIC(1, "骨科康复"),
    NEUROLOGICAL(2, "神经康复"),
    PAIN(3, "疼痛康复"),
    PEDIATRIC(4, "儿童康复"),
    POSTPARTUM(5, "产后康复"),
    CARDIOPULMONARY(6, "心肺康复"),
    GERIATRIC(7, "老年康复");

    private final Integer code;
    private final String description;

    RehabType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RehabType getByCode(int code) {
        for (RehabType type : RehabType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid RehabilitationType code: " + code);
    }
}
