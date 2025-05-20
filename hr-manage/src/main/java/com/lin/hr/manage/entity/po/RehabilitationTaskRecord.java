package com.lin.hr.manage.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 每日训练记录表：患者每日打卡与反馈
 */
public class RehabilitationTaskRecord implements Serializable {


	/**
	 * 记录ID
	 */
	private String recordId;

	/**
	 * 所属计划ID
	 */
	private String planId;

	/**
	 * 患者ID
	 */
	private String userId;

	/**
	 * 训练日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date trainingDate;

	/**
	 * 是否完成
	 */
	private Integer completed;

	/**
	 * 疼痛等级（0无~3重）
	 */
	private Integer painLevel;

	/**
	 * 患者备注
	 */
	private String comment;

	/**
	 * 记录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public void setRecordId(String recordId){
		this.recordId = recordId;
	}

	public String getRecordId(){
		return this.recordId;
	}

	public void setPlanId(String planId){
		this.planId = planId;
	}

	public String getPlanId(){
		return this.planId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setTrainingDate(Date trainingDate){
		this.trainingDate = trainingDate;
	}

	public Date getTrainingDate(){
		return this.trainingDate;
	}

	public void setCompleted(Integer completed){
		this.completed = completed;
	}

	public Integer getCompleted(){
		return this.completed;
	}

	public void setPainLevel(Integer painLevel){
		this.painLevel = painLevel;
	}

	public Integer getPainLevel(){
		return this.painLevel;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return this.comment;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	@Override
	public String toString (){
		return "记录ID:"+(recordId == null ? "空" : recordId)+"，所属计划ID:"+(planId == null ? "空" : planId)+"，患者ID:"+(userId == null ? "空" : userId)+"，训练日期:"+(trainingDate == null ? "空" : DateUtil.format(trainingDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()))+"，是否完成:"+(completed == null ? "空" : completed)+"，疼痛等级（0无~3重）:"+(painLevel == null ? "空" : painLevel)+"，患者备注:"+(comment == null ? "空" : comment)+"，记录时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
