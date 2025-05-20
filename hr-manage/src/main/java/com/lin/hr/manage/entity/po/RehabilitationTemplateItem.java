package com.lin.hr.manage.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 康复模板明细项
 */
@Data
public class RehabilitationTemplateItem implements Serializable {


	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * 所属康复模板ID
	 */
	private String templateId;

	/**
	 * 第几天（从1开始）
	 */
	private Integer dayIndex;

	/**
	 * 当天训练内容
	 */
	private String exerciseContent;

	/**
	 * 参考图片链接
	 */
	private String imageUrl;

	/**
	 * 参考视频链接
	 */
	private String videoUrl;

	/**
	 * 建议时长
	 */
	private Integer durationMinutes;

	/**
	 * 建议频次
	 */
	private Integer frequency;

	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@Override
	public String toString (){
		return "主键ID:"+(id == null ? "空" : id)+"，所属康复模板ID:"+(templateId == null ? "空" : templateId)+"，第几天（从1开始）:"+(dayIndex == null ? "空" : dayIndex)+"，当天训练内容:"+(exerciseContent == null ? "空" : exerciseContent)+"，参考图片链接:"+(imageUrl == null ? "空" : imageUrl)+"，参考视频链接:"+(videoUrl == null ? "空" : videoUrl)+"，建议时长:"+(durationMinutes == null ? "空" : durationMinutes)+"，建议频次:"+(frequency == null ? "空" : frequency)+"，createTime:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
