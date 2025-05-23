package com.lin.hr.manage.entity.po;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 康复类型表
 */
public class RehabilitationType implements Serializable {


	/**
	 * 类型ID
	 */
	private String typeId;

	/**
	 * 类型名称
	 */
	private String typeName;

	/**
	 * 类型编码
	 */
	private String typeCode;

	/**
	 * 类型描述
	 */
	private String description;

	/**
	 * 图标URL
	 */
	private String iconUrl;

	/**
	 * 排序号
	 */
	private Integer sortOrder;

	/**
	 * 状态：1-启用，0-禁用
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;


	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return this.typeId;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName(){
		return this.typeName;
	}

	public void setTypeCode(String typeCode){
		this.typeCode = typeCode;
	}

	public String getTypeCode(){
		return this.typeCode;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	public String getIconUrl(){
		return this.iconUrl;
	}

	public void setSortOrder(Integer sortOrder){
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder(){
		return this.sortOrder;
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

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	@Override
	public String toString (){
		return "类型ID:"+(typeId == null ? "空" : typeId)+"，类型名称:"+(typeName == null ? "空" : typeName)+"，类型编码:"+(typeCode == null ? "空" : typeCode)+"，类型描述:"+(description == null ? "空" : description)+"，图标URL:"+(iconUrl == null ? "空" : iconUrl)+"，排序号:"+(sortOrder == null ? "空" : sortOrder)+"，状态：1-启用，0-禁用:"+(status == null ? "空" : status)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
