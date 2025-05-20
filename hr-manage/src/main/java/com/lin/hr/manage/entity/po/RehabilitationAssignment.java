package com.lin.hr.manage.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 康复师-患者绑定表：管理多对多指导关系
 */
public class RehabilitationAssignment implements Serializable {


	/**
	 * 绑定关系ID
	 */
	private String assignmentId;

	/**
	 * 康复师ID
	 */
	private String therapistId;

	/**
	 * 患者ID
	 */
	private String userId;

	/**
	 * 指导类型（0普通 1专项）
	 */
	private Integer assignmentType;

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

	public void setTherapistId(String therapistId){
		this.therapistId = therapistId;
	}

	public String getTherapistId(){
		return this.therapistId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setAssignmentType(Integer assignmentType){
		this.assignmentType = assignmentType;
	}

	public Integer getAssignmentType(){
		return this.assignmentType;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	@Override
	public String toString (){
		return "绑定关系ID:"+(assignmentId == null ? "空" : assignmentId)+"，康复师ID:"+(therapistId == null ? "空" : therapistId)+"，患者ID:"+(userId == null ? "空" : userId)+"，指导类型（0普通 1专项）:"+(assignmentType == null ? "空" : assignmentType)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，最后更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，关系状态（0无效 1有效）:"+(status == null ? "空" : status);
	}
}
