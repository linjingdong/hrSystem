package com.lin.hr.im.entity.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/18 00:27
 **/
@Getter
public enum MessageStatusEnum {
    SENDING(0, "发送中"),
    SENDED(1, "已发送");

    private Integer status;
    private String desc;

    MessageStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static MessageStatusEnum getByStatus(Integer status) {
        for (MessageStatusEnum item : MessageStatusEnum.values()) {
            if (item.getStatus().equals(status)) {
                return item;
            }
        }
        return null;
    }
}
