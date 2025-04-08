package com.lin.hr.im.enums.apply;

import lombok.Getter;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/7 21:15
 **/
@Getter
public enum JoinTypeEnum {
    JOIN(0, "直接加入"),
    APPLY(1, "申请加入");

    private final Integer type;
    private final String desc;

    JoinTypeEnum(Integer status, String desc) {
        this.type = status;
        this.desc = desc;
    }
}
