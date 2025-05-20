package com.lin.hr.manage.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 个性化康复计划表：康复师为患者制定的方案
 */
public class RehabilitationPlan implements Serializable {


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

	public void setDoctorId(String doctorId){
		this.doctorId = doctorId;
	}

	public String getDoctorId(){
		return this.doctorId;
	}

	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}

	public String getTemplateId(){
		return this.templateId;
	}

	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}

	public Date getStartDate(){
		return this.startDate;
	}

	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}

	public Date getEndDate(){
		return this.endDate;
	}

	public void setCustomNote(String customNote){
		this.customNote = customNote;
	}

	public String getCustomNote(){
		return this.customNote;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setTemplateSnapshotJson(String templateSnapshotJson){
		this.templateSnapshotJson = templateSnapshotJson;
	}

	public String getTemplateSnapshotJson(){
		return this.templateSnapshotJson;
	}

	@Override
	public String toString (){
		return "计划ID:"+(planId == null ? "空" : planId)+"，患者ID:"+(userId == null ? "空" : userId)+"，康复师ID:"+(doctorId == null ? "空" : doctorId)+"，来源模板ID:"+(templateId == null ? "空" : templateId)+"，计划开始日期:"+(startDate == null ? "空" : DateUtil.format(startDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()))+"，计划结束日期:"+(endDate == null ? "空" : DateUtil.format(endDate, DateTimePatternEnum.YYYY_MM_DD.getPattern()))+"，个性化备注:"+(customNote == null ? "空" : customNote)+"，状态（0未开始 1进行中 2已完成 3中止）:"+(status == null ? "空" : status)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，模板快照（JSON格式）:"+(templateSnapshotJson == null ? "空" : templateSnapshotJson);
	}
}
