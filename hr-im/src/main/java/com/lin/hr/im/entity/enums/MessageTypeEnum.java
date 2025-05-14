package com.lin.hr.im.entity.enums;

import lombok.Getter;


/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/17 21:42
 **/
@Getter
public enum MessageTypeEnum {
    INIT(0, "", "连接ws获取信息"),
    ADD_FRIEND(1, "", "添加好友招呼消息"),
    CHAT(2, "", "普通聊天消息"),
    GROUP_CREATE(3, "群组已经创建好，可以和好友一起畅聊了", "群创建成功"),
    CONTACT_APPLY(4, "", "好友申请"),
    MEDIA_CHAT(5, "", "媒体文件"),
    FILE_UPLOAD(6, "", "文件上传完成"),
    FORCE_OFF_LINE(7, "", "强制下线"),
    DISSOLUTION_GROUP(8, "群聊已解散", "解散群聊"),
    ADD_GROUP(9, "%s加入了群组", "加入群聊"),
    CONTACT_NAME_UPDATE(10, "", "更新昵称"),
    LEAVE_GROUP(11, "%s退出了群聊", "退出群聊"),
    REMOVE_GROUP(12, "%s被管理员移出了群聊", "被管理员移出了群聊"),
    ADD_FRIEND_SELF(13, "", "添加好友招呼消息发送给自己");

    private final Integer type;
    private final String initMessage;
    private final String desc;

    MessageTypeEnum(Integer type, String initMessage, String desc) {
        this.type = type;
        this.initMessage = initMessage;
        this.desc = desc;
    }

    public static MessageTypeEnum getByType(Integer type) {
        for (MessageTypeEnum e : MessageTypeEnum.values()) {
            if (e.type.equals(type)) {
                return e;
            }
        }
        return null;
    }

    public static MessageTypeEnum getByName(String name) {
        for (MessageTypeEnum e : MessageTypeEnum.values()) {
            if (e.name().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
