package com.lin.hr.manage.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 康复计划反馈表：患者对完成计划的评分与评价
 */
public class RehabilitationFeedback implements Serializable {


	/**
	 * 反馈ID
	 */
	private String feedbackId;

	/**
	 * 关联计划ID
	 */
	private String planId;

	/**
	 * 患者ID
	 */
	private String userId;

	/**
	 * 评分（1~5）
	 */
	private Integer score;

	/**
	 * 文字反馈
	 */
	private String feedback;

	/**
	 * 提交时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date submitTime;


	public void setFeedbackId(String feedbackId){
		this.feedbackId = feedbackId;
	}

	public String getFeedbackId(){
		return this.feedbackId;
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

	public void setScore(Integer score){
		this.score = score;
	}

	public Integer getScore(){
		return this.score;
	}

	public void setFeedback(String feedback){
		this.feedback = feedback;
	}

	public String getFeedback(){
		return this.feedback;
	}

	public void setSubmitTime(Date submitTime){
		this.submitTime = submitTime;
	}

	public Date getSubmitTime(){
		return this.submitTime;
	}

	@Override
	public String toString (){
		return "反馈ID:"+(feedbackId == null ? "空" : feedbackId)+"，关联计划ID:"+(planId == null ? "空" : planId)+"，患者ID:"+(userId == null ? "空" : userId)+"，评分（1~5）:"+(score == null ? "空" : score)+"，文字反馈:"+(feedback == null ? "空" : feedback)+"，提交时间:"+(submitTime == null ? "空" : DateUtil.format(submitTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
