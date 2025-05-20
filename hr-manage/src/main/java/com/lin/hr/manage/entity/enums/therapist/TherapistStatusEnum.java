package com.lin.hr.manage.entity.enums.therapist;

import lombok.Getter;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/19 13:07
 **/
@Getter
public enum TherapistStatusEnum {
    LEAVE_JOB(0, "离职"),
    ON_JOB(1, "在职");

    private final int status;
    private final String name;

    TherapistStatusEnum(int status, String name) {
        this.status = status;
        this.name = name;
    }
}
