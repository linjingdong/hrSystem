package com.lin.hr.manage.entity.po;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 康复训练模板表：后台预设标准训练方案
 */
@Data
public class RehabilitationTemplate implements Serializable {
	/**
	 * 模板ID
	 */
	private String templateId;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 康复类型ID
	 */
	private String typeId;

	/**
	 * 总训练天数
	 */
	private Integer totalDays;

	/**
	 * 适用人群说明
	 */
	private String suitableFor;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 康复类型名称
	 */
	private String typeName;

	@Override
	public String toString (){
		return "模板ID:"+(templateId == null ? "空" : templateId)+"，模板名称:"+(templateName == null ? "空" : templateName)+"，康复类型ID:"+(typeId == null ? "空" : typeId)+"，总训练天数:"+(totalDays == null ? "空" : totalDays)+"，适用人群说明:"+(suitableFor == null ? "空" : suitableFor)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
