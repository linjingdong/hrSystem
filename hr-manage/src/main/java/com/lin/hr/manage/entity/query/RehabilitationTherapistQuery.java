package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 康复师信息表：存储康复师专业资料参数
 */
public class RehabilitationTherapistQuery extends BaseParam {


	/**
	 * 康复师ID，关联user_info.user_id
	 */
	private String therapistId;

	private String therapistIdFuzzy;

	/**
	 * 擅长的康复类型
	 */
	private String rehabType;

	private String rehabTypeFuzzy;

	/**
	 * 擅长领域
	 */
	private String specialization;

	private String specializationFuzzy;

	/**
	 * 职业资质证书信息
	 */
	private String qualification;

	private String qualificationFuzzy;

	/**
	 * 从业年限
	 */
	private Integer experienceYears;

	/**
	 * 个人简介
	 */
	private String introduction;

	private String introductionFuzzy;

	/**
	 * 联系电话
	 */
	private String phone;

	private String phoneFuzzy;

	/**
	 * 联系邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 在职状态（0离职 1在职）
	 */
	private Integer status;

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

	public void setRehabType(String rehabType){
		this.rehabType = rehabType;
	}

	public String getRehabType(){
		return this.rehabType;
	}

	public void setRehabTypeFuzzy(String rehabTypeFuzzy){
		this.rehabTypeFuzzy = rehabTypeFuzzy;
	}

	public String getRehabTypeFuzzy(){
		return this.rehabTypeFuzzy;
	}

	public void setSpecialization(String specialization){
		this.specialization = specialization;
	}

	public String getSpecialization(){
		return this.specialization;
	}

	public void setSpecializationFuzzy(String specializationFuzzy){
		this.specializationFuzzy = specializationFuzzy;
	}

	public String getSpecializationFuzzy(){
		return this.specializationFuzzy;
	}

	public void setQualification(String qualification){
		this.qualification = qualification;
	}

	public String getQualification(){
		return this.qualification;
	}

	public void setQualificationFuzzy(String qualificationFuzzy){
		this.qualificationFuzzy = qualificationFuzzy;
	}

	public String getQualificationFuzzy(){
		return this.qualificationFuzzy;
	}

	public void setExperienceYears(Integer experienceYears){
		this.experienceYears = experienceYears;
	}

	public Integer getExperienceYears(){
		return this.experienceYears;
	}

	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}

	public String getIntroduction(){
		return this.introduction;
	}

	public void setIntroductionFuzzy(String introductionFuzzy){
		this.introductionFuzzy = introductionFuzzy;
	}

	public String getIntroductionFuzzy(){
		return this.introductionFuzzy;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setPhoneFuzzy(String phoneFuzzy){
		this.phoneFuzzy = phoneFuzzy;
	}

	public String getPhoneFuzzy(){
		return this.phoneFuzzy;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setEmailFuzzy(String emailFuzzy){
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy(){
		return this.emailFuzzy;
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

}
