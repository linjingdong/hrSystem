package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 康复模板明细项参数
 */
public class RehabilitationTemplateItemQuery extends BaseParam {


	/**
	 * 主键ID
	 */
	private String id;

	private String idFuzzy;

	/**
	 * 所属康复模板ID
	 */
	private String templateId;

	private String templateIdFuzzy;

	/**
	 * 第几天（从1开始）
	 */
	private Integer dayIndex;

	/**
	 * 当天训练内容
	 */
	private String exerciseContent;

	private String exerciseContentFuzzy;

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
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;


	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return this.id;
	}

	public void setIdFuzzy(String idFuzzy){
		this.idFuzzy = idFuzzy;
	}

	public String getIdFuzzy(){
		return this.idFuzzy;
	}

	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}

	public String getTemplateId(){
		return this.templateId;
	}

	public void setTemplateIdFuzzy(String templateIdFuzzy){
		this.templateIdFuzzy = templateIdFuzzy;
	}

	public String getTemplateIdFuzzy(){
		return this.templateIdFuzzy;
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
