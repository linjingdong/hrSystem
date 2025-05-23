package com.lin.hr.manage.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.manage.entity.po.RehabilitationPlanItem;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/21 10:14
 **/
@Data
public class RehabilitationPlanDto {
    /**
     * 计划ID
     */
    private String planId;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 计划结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    List<RehabilitationPlanItem> itemList;
}
