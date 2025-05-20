package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 康复计划反馈表：患者对完成计划的评分与评价参数
 */
public class RehabilitationFeedbackQuery extends BaseParam {


	/**
	 * 反馈ID
	 */
	private String feedbackId;

	private String feedbackIdFuzzy;

	/**
	 * 关联计划ID
	 */
	private String planId;

	private String planIdFuzzy;

	/**
	 * 患者ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 评分（1~5）
	 */
	private Integer score;

	/**
	 * 文字反馈
	 */
	private String feedback;

	private String feedbackFuzzy;

	/**
	 * 提交时间
	 */
	private String submitTime;

	private String submitTimeStart;

	private String submitTimeEnd;


	public void setFeedbackId(String feedbackId){
		this.feedbackId = feedbackId;
	}

	public String getFeedbackId(){
		return this.feedbackId;
	}

	public void setFeedbackIdFuzzy(String feedbackIdFuzzy){
		this.feedbackIdFuzzy = feedbackIdFuzzy;
	}

	public String getFeedbackIdFuzzy(){
		return this.feedbackIdFuzzy;
	}

	public void setPlanId(String planId){
		this.planId = planId;
	}

	public String getPlanId(){
		return this.planId;
	}

	public void setPlanIdFuzzy(String planIdFuzzy){
		this.planIdFuzzy = planIdFuzzy;
	}

	public String getPlanIdFuzzy(){
		return this.planIdFuzzy;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserIdFuzzy(String userIdFuzzy){
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy(){
		return this.userIdFuzzy;
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

	public void setFeedbackFuzzy(String feedbackFuzzy){
		this.feedbackFuzzy = feedbackFuzzy;
	}

	public String getFeedbackFuzzy(){
		return this.feedbackFuzzy;
	}

	public void setSubmitTime(String submitTime){
		this.submitTime = submitTime;
	}

	public String getSubmitTime(){
		return this.submitTime;
	}

	public void setSubmitTimeStart(String submitTimeStart){
		this.submitTimeStart = submitTimeStart;
	}

	public String getSubmitTimeStart(){
		return this.submitTimeStart;
	}
	public void setSubmitTimeEnd(String submitTimeEnd){
		this.submitTimeEnd = submitTimeEnd;
	}

	public String getSubmitTimeEnd(){
		return this.submitTimeEnd;
	}

}
