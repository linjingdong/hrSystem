package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 康复计划反馈表：患者对完成计划的评分与评价 数据库操作接口
 */
public interface RehabilitationFeedbackMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据FeedbackId更新
	 */
	 Integer updateByFeedbackId(@Param("bean") T t,@Param("feedbackId") String feedbackId);


	/**
	 * 根据FeedbackId删除
	 */
	 Integer deleteByFeedbackId(@Param("feedbackId") String feedbackId);


	/**
	 * 根据FeedbackId获取对象
	 */
	 T selectByFeedbackId(@Param("feedbackId") String feedbackId);


}
