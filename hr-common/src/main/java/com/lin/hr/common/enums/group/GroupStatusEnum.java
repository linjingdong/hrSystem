package com.lin.hr.common.enums.group;

import lombok.Getter;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/6 23:09
 **/
@Getter
public enum GroupStatusEnum {
    NORMAL(1, "正常"),
    DISSOLUTION(0, "解散");

    private Integer status;
    private String desc;

    GroupStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static GroupStatusEnum getByStatus(Integer status) {
        for (GroupStatusEnum item: GroupStatusEnum.values()) {
            if (status.equals(item.getStatus())) {
                return item;
            }
        }
        return null;
    }
}
