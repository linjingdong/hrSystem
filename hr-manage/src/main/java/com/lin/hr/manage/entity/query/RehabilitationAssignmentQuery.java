package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 康复师-患者绑定表：管理多对多指导关系参数
 */
public class RehabilitationAssignmentQuery extends BaseParam {


	/**
	 * 绑定关系ID
	 */
	private String assignmentId;

	private String assignmentIdFuzzy;

	/**
	 * 康复师ID
	 */
	private String therapistId;

	private String therapistIdFuzzy;

	/**
	 * 患者ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 指导类型（0普通 1专项）
	 */
	private Integer assignmentType;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 最后更新时间
	 */
	private String updateTime;

	private String updateTimeStart;

	private String updateTimeEnd;

	/**
	 * 关系状态（0无效 1有效）
	 */
	private Integer status;


	public void setAssignmentId(String assignmentId){
		this.assignmentId = assignmentId;
	}

	public String getAssignmentId(){
		return this.assignmentId;
	}

	public void setAssignmentIdFuzzy(String assignmentIdFuzzy){
		this.assignmentIdFuzzy = assignmentIdFuzzy;
	}

	public String getAssignmentIdFuzzy(){
		return this.assignmentIdFuzzy;
	}

	public void setTherapistId(String therapistId){
		this.therapistId = therapistId;
	}

	public String getTherapistId(){
		return this.therapistId;
	}

	public void setTherapistIdFuzzy(String therapistIdFuzzy){
		this.therapistIdFuzzy = therapistIdFuzzy;
	}

	public String getTherapistIdFuzzy(){
		return this.therapistIdFuzzy;
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

	public void setAssignmentType(Integer assignmentType){
		this.assignmentType = assignmentType;
	}

	public Integer getAssignmentType(){
		return this.assignmentType;
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

	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateTime(){
		return this.updateTime;
	}

	public void setUpdateTimeStart(String updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}

	public String getUpdateTimeStart(){
		return this.updateTimeStart;
	}
	public void setUpdateTimeEnd(String updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getUpdateTimeEnd(){
		return this.updateTimeEnd;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

}
