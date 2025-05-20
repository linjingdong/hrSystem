package com.lin.hr.manage.entity.query;

import lombok.Data;

import java.util.Date;


/**
 * 康复训练模板表：后台预设标准训练方案参数
 */
@Data
public class RehabilitationTemplateQuery extends BaseParam {
	/**
	 * 模板ID
	 */
	private String templateId;

	private String templateIdFuzzy;

	/**
	 * 模板名称
	 */
	private String templateName;

	private String templateNameFuzzy;

	/**
	 * 康复类型ID
	 */
	private String typeId;

	private String typeIdFuzzy;

	/**
	 * 总训练天数
	 */
	private Integer totalDays;

	/**
	 * 适用人群说明
	 */
	private String suitableFor;

	private String suitableForFuzzy;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;
}
