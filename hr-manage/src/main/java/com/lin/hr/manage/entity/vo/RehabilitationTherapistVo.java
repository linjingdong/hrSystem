package com.lin.hr.manage.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/21 01:15
 **/
@Data
public class RehabilitationTherapistVo {

    /**
     * 康复师ID，关联user_info.user_id
     */
    private String therapistId;

    /**
     * 擅长的康复类型
     */
    private String rehabType;

    /**
     * 康复类型名称
     */
    private String typeName;

    /**
     * 擅长领域
     */
    private String specialization;

    /**
     * 职业资质证书信息
     */
    private String qualification;

    /**
     * 从业年限
     */
    private Integer experienceYears;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 联系电话
     */
    @JsonIgnore
    private String phone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 在职状态（0离职 1在职）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String userName;
}
