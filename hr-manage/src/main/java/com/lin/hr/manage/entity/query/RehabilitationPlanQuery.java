package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 个性化康复计划表：康复师为患者制定的方案参数
 */
public class RehabilitationPlanQuery extends BaseParam {


	/**
	 * 计划ID
	 */
	private String planId;

	private String planIdFuzzy;

	/**
	 * 患者ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 康复师ID
	 */
	private String doctorId;

	private String doctorIdFuzzy;

	/**
	 * 来源模板ID
	 */
	private String templateId;

	private String templateIdFuzzy;

	/**
	 * 计划开始日期
	 */
	private String startDate;

	private String startDateStart;

	private String startDateEnd;

	/**
	 * 计划结束日期
	 */
	private String endDate;

	private String endDateStart;

	private String endDateEnd;

	/**
	 * 个性化备注
	 */
	private String customNote;

	private String customNoteFuzzy;

	/**
	 * 状态（0未开始 1进行中 2已完成 3中止）
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 模板快照（JSON格式）
	 */
	private String templateSnapshotJson;

	private String templateSnapshotJsonFuzzy;

	/**
	 * 患者姓名
	 */
	private String username;

	private String usernameFuzzy;

	/**
	 * 医生姓名
	 */
	private String doctorname;

	private String doctornameFuzzy;


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

	public void setDoctorId(String doctorId){
		this.doctorId = doctorId;
	}

	public String getDoctorId(){
		return this.doctorId;
	}

	public void setDoctorIdFuzzy(String doctorIdFuzzy){
		this.doctorIdFuzzy = doctorIdFuzzy;
	}

	public String getDoctorIdFuzzy(){
		return this.doctorIdFuzzy;
	}

	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}

	public String getTemplateId(){
		return this.templateId;
	}

	public void setTemplateIdFuzzy(String templateIdFuzzy){
		this.templateIdFuzzy = templateIdFuzzy;
	}

	public String getTemplateIdFuzzy(){
		return this.templateIdFuzzy;
	}

	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getStartDate(){
		return this.startDate;
	}

	public void setStartDateStart(String startDateStart){
		this.startDateStart = startDateStart;
	}

	public String getStartDateStart(){
		return this.startDateStart;
	}
	public void setStartDateEnd(String startDateEnd){
		this.startDateEnd = startDateEnd;
	}

	public String getStartDateEnd(){
		return this.startDateEnd;
	}

	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public String getEndDate(){
		return this.endDate;
	}

	public void setEndDateStart(String endDateStart){
		this.endDateStart = endDateStart;
	}

	public String getEndDateStart(){
		return this.endDateStart;
	}
	public void setEndDateEnd(String endDateEnd){
		this.endDateEnd = endDateEnd;
	}

	public String getEndDateEnd(){
		return this.endDateEnd;
	}

	public void setCustomNote(String customNote){
		this.customNote = customNote;
	}

	public String getCustomNote(){
		return this.customNote;
	}

	public void setCustomNoteFuzzy(String customNoteFuzzy){
		this.customNoteFuzzy = customNoteFuzzy;
	}

	public String getCustomNoteFuzzy(){
		return this.customNoteFuzzy;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
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

	public void setTemplateSnapshotJson(String templateSnapshotJson){
		this.templateSnapshotJson = templateSnapshotJson;
	}

	public String getTemplateSnapshotJson(){
		return this.templateSnapshotJson;
	}

	public void setTemplateSnapshotJsonFuzzy(String templateSnapshotJsonFuzzy){
		this.templateSnapshotJsonFuzzy = templateSnapshotJsonFuzzy;
	}

	public String getTemplateSnapshotJsonFuzzy(){
		return this.templateSnapshotJsonFuzzy;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setUsernameFuzzy(String usernameFuzzy){
		this.usernameFuzzy = usernameFuzzy;
	}

	public String getUsernameFuzzy(){
		return this.usernameFuzzy;
	}

	public void setDoctorname(String doctorname){
		this.doctorname = doctorname;
	}

	public String getDoctorname(){
		return this.doctorname;
	}

	public void setDoctornameFuzzy(String doctornameFuzzy){
		this.doctornameFuzzy = doctornameFuzzy;
	}

	public String getDoctornameFuzzy(){
		return this.doctornameFuzzy;
	}

}
