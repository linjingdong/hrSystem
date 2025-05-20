package com.lin.hr.manage.entity.query;

import java.util.Date;


/**
 * 参数
 */
public class AiConsultationRecordQuery extends BaseParam {


	/**
	 * 
	 */
	private String consultationId;

	private String consultationIdFuzzy;

	/**
	 * 
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 
	 */
	private String questionContent;

	private String questionContentFuzzy;

	/**
	 * 
	 */
	private String answerContent;

	private String answerContentFuzzy;

	/**
	 * 
	 */
	private String sendTime;

	private String sendTimeStart;

	private String sendTimeEnd;

	/**
	 * 
	 */
	private String receiveTime;

	private String receiveTimeStart;

	private String receiveTimeEnd;


	public void setConsultationId(String consultationId){
		this.consultationId = consultationId;
	}

	public String getConsultationId(){
		return this.consultationId;
	}

	public void setConsultationIdFuzzy(String consultationIdFuzzy){
		this.consultationIdFuzzy = consultationIdFuzzy;
	}

	public String getConsultationIdFuzzy(){
		return this.consultationIdFuzzy;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setUserIdFuzzy(String userIdFuzzy){
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getUserIdFuzzy(){
		return this.userIdFuzzy;
	}

	public void setQuestionContent(String questionContent){
		this.questionContent = questionContent;
	}

	public String getQuestionContent(){
		return this.questionContent;
	}

	public void setQuestionContentFuzzy(String questionContentFuzzy){
		this.questionContentFuzzy = questionContentFuzzy;
	}

	public String getQuestionContentFuzzy(){
		return this.questionContentFuzzy;
	}

	public void setAnswerContent(String answerContent){
		this.answerContent = answerContent;
	}

	public String getAnswerContent(){
		return this.answerContent;
	}

	public void setAnswerContentFuzzy(String answerContentFuzzy){
		this.answerContentFuzzy = answerContentFuzzy;
	}

	public String getAnswerContentFuzzy(){
		return this.answerContentFuzzy;
	}

	public void setSendTime(String sendTime){
		this.sendTime = sendTime;
	}

	public String getSendTime(){
		return this.sendTime;
	}

	public void setSendTimeStart(String sendTimeStart){
		this.sendTimeStart = sendTimeStart;
	}

	public String getSendTimeStart(){
		return this.sendTimeStart;
	}
	public void setSendTimeEnd(String sendTimeEnd){
		this.sendTimeEnd = sendTimeEnd;
	}

	public String getSendTimeEnd(){
		return this.sendTimeEnd;
	}

	public void setReceiveTime(String receiveTime){
		this.receiveTime = receiveTime;
	}

	public String getReceiveTime(){
		return this.receiveTime;
	}

	public void setReceiveTimeStart(String receiveTimeStart){
		this.receiveTimeStart = receiveTimeStart;
	}

	public String getReceiveTimeStart(){
		return this.receiveTimeStart;
	}
	public void setReceiveTimeEnd(String receiveTimeEnd){
		this.receiveTimeEnd = receiveTimeEnd;
	}

	public String getReceiveTimeEnd(){
		return this.receiveTimeEnd;
	}

}
