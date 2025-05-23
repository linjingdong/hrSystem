package com.lin.hr.manage.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/21 09:30
 **/
@Data
public class RehabilitationPlanVO {
    /**
     * 计划ID
     */
    private String planId;

    private String planName;

    /**
     * 患者ID
     */
    private String userId;

    /**
     * 康复师ID
     */
    private String doctorId;

    /**
     * 来源模板ID
     */
    private String templateId;

    /**
     * 计划开始日期
     */
    private Date startDate;

    /**
     * 计划结束日期
     */
    private Date endDate;

    /**
     * 个性化备注
     */
    private String customNote;

    /**
     * 状态（0未开始 1进行中 2已完成 3中止）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 模板快照（JSON格式）
     */
    private String templateSnapshotJson;

    /**
     * 患者姓名
     */
    private String username;

    /**
     * 医生姓名
     */
    private String doctorname;

    private Integer totalDay;

    private Integer remainingDays;

    private Integer progressRate;
}
