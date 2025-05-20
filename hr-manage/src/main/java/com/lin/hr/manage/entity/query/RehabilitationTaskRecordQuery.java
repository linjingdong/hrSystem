package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 每日训练记录表：患者每日打卡与反馈参数
 */
public class RehabilitationTaskRecordQuery extends BaseParam {


	/**
	 * 记录ID
	 */
	private String recordId;

	private String recordIdFuzzy;

	/**
	 * 所属计划ID
	 */
	private String planId;

	private String planIdFuzzy;

	/**
	 * 患者ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 训练日期
	 */
	private String trainingDate;

	private String trainingDateStart;

	private String trainingDateEnd;

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

	private String commentFuzzy;

	/**
	 * 记录时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;


	public void setRecordId(String recordId){
		this.recordId = recordId;
	}

	public String getRecordId(){
		return this.recordId;
	}

	public void setRecordIdFuzzy(String recordIdFuzzy){
		this.recordIdFuzzy = recordIdFuzzy;
	}

	public String getRecordIdFuzzy(){
		return this.recordIdFuzzy;
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

	public void setTrainingDate(String trainingDate){
		this.trainingDate = trainingDate;
	}

	public String getTrainingDate(){
		return this.trainingDate;
	}

	public void setTrainingDateStart(String trainingDateStart){
		this.trainingDateStart = trainingDateStart;
	}

	public String getTrainingDateStart(){
		return this.trainingDateStart;
	}
	public void setTrainingDateEnd(String trainingDateEnd){
		this.trainingDateEnd = trainingDateEnd;
	}

	public String getTrainingDateEnd(){
		return this.trainingDateEnd;
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

	public void setCommentFuzzy(String commentFuzzy){
		this.commentFuzzy = commentFuzzy;
	}

	public String getCommentFuzzy(){
		return this.commentFuzzy;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setCreateTimeStart(String createTimeStart){
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart(){
		return this.createTimeStart;
	}
	public void setCreateTimeEnd(String createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd(){
		return this.createTimeEnd;
	}

}
