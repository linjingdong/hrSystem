package com.lin.hr.im.enums.apply;

import com.lin.hr.common.exception.BusinessException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/7 21:21
 **/
@Getter
public enum UserContactApplyStatusEnum {
    INIT(0, "待处理"),
    PASS(1, "已同意"),
    REJECT(2, "已拒绝"),
    BLACKLIST(3, "已拉黑");

    private final Integer status;
    private final String desc;

    UserContactApplyStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static UserContactApplyStatusEnum getByStatus(String name) {
        try {
            if (StringUtils.isBlank(name)) {
                return null;
            } else {
                return UserContactApplyStatusEnum.valueOf(name.toUpperCase());
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static UserContactApplyStatusEnum getByStatus(Integer status) {
        if (null == status) {
            return null;
        } else {
            for (UserContactApplyStatusEnum statusEnum : UserContactApplyStatusEnum.values()) {
                if (status.equals(statusEnum.getStatus())) {
                    return statusEnum;
                }
            }
            return null;
        }
    }
}
