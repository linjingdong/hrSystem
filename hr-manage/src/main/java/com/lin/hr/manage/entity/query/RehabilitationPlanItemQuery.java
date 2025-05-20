package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 康复计划明细项（个性化）参数
 */
public class RehabilitationPlanItemQuery extends BaseParam {


	/**
	 * 明细ID
	 */
	private String detailId;

	private String detailIdFuzzy;

	/**
	 * 所属康复计划ID
	 */
	private String planId;

	private String planIdFuzzy;

	/**
	 * 子项训练日期
	 */
	private Integer dayIndex;

	/**
	 * 训练内容说明
	 */
	private String exerciseContent;

	private String exerciseContentFuzzy;

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

	private String imageUrlFuzzy;

	/**
	 * 参考视频链接
	 */
	private String videoUrl;

	private String videoUrlFuzzy;

	/**
	 * 
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;


	public void setDetailId(String detailId){
		this.detailId = detailId;
	}

	public String getDetailId(){
		return this.detailId;
	}

	public void setDetailIdFuzzy(String detailIdFuzzy){
		this.detailIdFuzzy = detailIdFuzzy;
	}

	public String getDetailIdFuzzy(){
		return this.detailIdFuzzy;
	}

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

	public void setExerciseContentFuzzy(String exerciseContentFuzzy){
		this.exerciseContentFuzzy = exerciseContentFuzzy;
	}

	public String getExerciseContentFuzzy(){
		return this.exerciseContentFuzzy;
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

	public void setImageUrlFuzzy(String imageUrlFuzzy){
		this.imageUrlFuzzy = imageUrlFuzzy;
	}

	public String getImageUrlFuzzy(){
		return this.imageUrlFuzzy;
	}

	public void setVideoUrl(String videoUrl){
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl(){
		return this.videoUrl;
	}

	public void setVideoUrlFuzzy(String videoUrlFuzzy){
		this.videoUrlFuzzy = videoUrlFuzzy;
	}

	public String getVideoUrlFuzzy(){
		return this.videoUrlFuzzy;
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

}
