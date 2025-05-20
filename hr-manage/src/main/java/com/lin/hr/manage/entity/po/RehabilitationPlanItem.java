package com.lin.hr.manage.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 康复计划明细项（个性化）
 */
public class RehabilitationPlanItem implements Serializable {


	/**
	 * 明细ID
	 */
	private String detailId;

	/**
	 * 所属康复计划ID
	 */
	private String planId;

	/**
	 * 子项训练日期
	 */
	private Integer dayIndex;

	/**
	 * 训练内容说明
	 */
	private String exerciseContent;

	/**
	 * 训练时长
	 */
	private Integer durationMinutes;

	/**
	 * 训练频次
	 */
	private Integer frequency;

	/**
	 * 参考图片链接
	 */
	private String imageUrl;

	/**
	 * 参考视频链接
	 */
	private String videoUrl;

	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;


	public void setDetailId(String detailId){
		this.detailId = detailId;
	}

	public String getDetailId(){
		return this.detailId;
	}

	public void setPlanId(String planId){
		this.planId = planId;
	}

	public String getPlanId(){
		return this.planId;
	}

	public void setDayIndex(Integer dayIndex){
		this.dayIndex = dayIndex;
	}

	public Integer getDayIndex(){
		return this.dayIndex;
	}

	public void setExerciseContent(String exerciseContent){
		this.exerciseContent = exerciseContent;
	}

	public String getExerciseContent(){
		return this.exerciseContent;
	}

	public void setDurationMinutes(Integer durationMinutes){
		this.durationMinutes = durationMinutes;
	}

	public Integer getDurationMinutes(){
		return this.durationMinutes;
	}

	public void setFrequency(Integer frequency){
		this.frequency = frequency;
	}

	public Integer getFrequency(){
		return this.frequency;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return this.imageUrl;
	}

	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl(){
		return this.videoUrl;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	@Override
	public String toString (){
		return "明细ID:"+(detailId == null ? "空" : detailId)+"，所属康复计划ID:"+(planId == null ? "空" : planId)+"，子项训练日期:"+(dayIndex == null ? "空" : dayIndex)+"，训练内容说明:"+(exerciseContent == null ? "空" : exerciseContent)+"，训练时长:"+(durationMinutes == null ? "空" : durationMinutes)+"，训练频次:"+(frequency == null ? "空" : frequency)+"，参考图片链接:"+(imageUrl == null ? "空" : imageUrl)+"，参考视频链接:"+(videoUrl == null ? "空" : videoUrl)+"，createTime:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
