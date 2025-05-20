package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 每日训练记录表：患者每日打卡与反馈 数据库操作接口
 */
public interface RehabilitationTaskRecordMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据RecordId更新
	 */
	 Integer updateByRecordId(@Param("bean") T t,@Param("recordId") String recordId);


	/**
	 * 根据RecordId删除
	 */
	 Integer deleteByRecordId(@Param("recordId") String recordId);


	/**
	 * 根据RecordId获取对象
	 */
	 T selectByRecordId(@Param("recordId") String recordId);


	/**
	 * 根据PlanIdAndTrainingDate更新
	 */
	 Integer updateByPlanIdAndTrainingDate(@Param("bean") T t,@Param("planId") String planId,@Param("trainingDate") Date trainingDate);


	/**
	 * 根据PlanIdAndTrainingDate删除
	 */
	 Integer deleteByPlanIdAndTrainingDate(@Param("planId") String planId,@Param("trainingDate") Date trainingDate);


	/**
	 * 根据PlanIdAndTrainingDate获取对象
	 */
	 T selectByPlanIdAndTrainingDate(@Param("planId") String planId,@Param("trainingDate") Date trainingDate);


}
