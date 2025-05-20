package com.lin.hr.manage.entity.po;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 
 */
public class AiConsultationRecord implements Serializable {


	/**
	 * 
	 */
	private String consultationId;

	/**
	 * 
	 */
	private String userId;

	/**
	 * 
	 */
	private String questionContent;

	/**
	 * 
	 */
	private String answerContent;

	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sendTime;

	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date receiveTime;


	public void setConsultationId(String consultationId){
		this.consultationId = consultationId;
	}

	public String getConsultationId(){
		return this.consultationId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return this.userId;
	}

	public void setQuestionContent(String questionContent){
		this.questionContent = questionContent;
	}

	public String getQuestionContent(){
		return this.questionContent;
	}

	public void setAnswerContent(String answerContent){
		this.answerContent = answerContent;
	}

	public String getAnswerContent(){
		return this.answerContent;
	}

	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}

	public Date getSendTime(){
		return this.sendTime;
	}

	public void setReceiveTime(Date receiveTime){
		this.receiveTime = receiveTime;
	}

	public Date getReceiveTime(){
		return this.receiveTime;
	}

	@Override
	public String toString (){
		return "consultationId:"+(consultationId == null ? "空" : consultationId)+"，userId:"+(userId == null ? "空" : userId)+"，questionContent:"+(questionContent == null ? "空" : questionContent)+"，answerContent:"+(answerContent == null ? "空" : answerContent)+"，sendTime:"+(sendTime == null ? "空" : DateUtil.format(sendTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，receiveTime:"+(receiveTime == null ? "空" : DateUtil.format(receiveTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
	}
}
