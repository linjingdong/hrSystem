package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 康复类型表参数
 */
public class RehabilitationTypeQuery extends BaseParam {


	/**
	 * 类型ID
	 */
	private String typeId;

	private String typeIdFuzzy;

	/**
	 * 类型名称
	 */
	private String typeName;

	private String typeNameFuzzy;

	/**
	 * 类型编码
	 */
	private String typeCode;

	private String typeCodeFuzzy;

	/**
	 * 类型描述
	 */
	private String description;

	private String descriptionFuzzy;

	/**
	 * 图标URL
	 */
	private String iconUrl;

	private String iconUrlFuzzy;

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
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 更新时间
	 */
	private String updateTime;

	private String updateTimeStart;

	private String updateTimeEnd;


	public void setTypeId(String typeId){
		this.typeId = typeId;
	}

	public String getTypeId(){
		return this.typeId;
	}

	public void setTypeIdFuzzy(String typeIdFuzzy){
		this.typeIdFuzzy = typeIdFuzzy;
	}

	public String getTypeIdFuzzy(){
		return this.typeIdFuzzy;
	}

	public void setTypeName(String typeName){
		this.typeName = typeName;
	}

	public String getTypeName(){
		return this.typeName;
	}

	public void setTypeNameFuzzy(String typeNameFuzzy){
		this.typeNameFuzzy = typeNameFuzzy;
	}

	public String getTypeNameFuzzy(){
		return this.typeNameFuzzy;
	}

	public void setTypeCode(String typeCode){
		this.typeCode = typeCode;
	}

	public String getTypeCode(){
		return this.typeCode;
	}

	public void setTypeCodeFuzzy(String typeCodeFuzzy){
		this.typeCodeFuzzy = typeCodeFuzzy;
	}

	public String getTypeCodeFuzzy(){
		return this.typeCodeFuzzy;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setDescriptionFuzzy(String descriptionFuzzy){
		this.descriptionFuzzy = descriptionFuzzy;
	}

	public String getDescriptionFuzzy(){
		return this.descriptionFuzzy;
	}

	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}

	public String getIconUrl(){
		return this.iconUrl;
	}

	public void setIconUrlFuzzy(String iconUrlFuzzy){
		this.iconUrlFuzzy = iconUrlFuzzy;
	}

	public String getIconUrlFuzzy(){
		return this.iconUrlFuzzy;
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
