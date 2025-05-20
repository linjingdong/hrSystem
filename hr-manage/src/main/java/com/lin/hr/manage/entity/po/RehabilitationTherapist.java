package com.lin.hr.manage.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 康复师信息表：存储康复师专业资料
 */
@Data
public class RehabilitationTherapist implements Serializable {


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

	@Override
	public String toString (){
		return "康复师ID，关联user_info.user_id:"+(therapistId == null ? "空" : therapistId)+"，擅长的康复类型:"+(rehabType == null ? "空" : rehabType)+"，擅长领域:"+(specialization == null ? "空" : specialization)+"，职业资质证书信息:"+(qualification == null ? "空" : qualification)+"，从业年限:"+(experienceYears == null ? "空" : experienceYears)+"，个人简介:"+(introduction == null ? "空" : introduction)+"，联系电话:"+(phone == null ? "空" : phone)+"，联系邮箱:"+(email == null ? "空" : email)+"，在职状态（0离职 1在职）:"+(status == null ? "空" : status)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，最后更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
