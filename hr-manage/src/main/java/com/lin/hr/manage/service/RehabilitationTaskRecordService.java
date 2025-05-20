package com.lin.hr.manage.service;

import java.util.Date;
import java.util.List;

import com.lin.hr.manage.entity.query.RehabilitationTaskRecordQuery;
import com.lin.hr.manage.entity.po.RehabilitationTaskRecord;
import com.lin.hr.common.vo.PaginationResultVO;


/**
 * 每日训练记录表：患者每日打卡与反馈 业务接口
 */
public interface RehabilitationTaskRecordService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationTaskRecord> findListByParam(RehabilitationTaskRecordQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationTaskRecordQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationTaskRecord> findListByPage(RehabilitationTaskRecordQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationTaskRecord bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationTaskRecord> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationTaskRecord> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationTaskRecord bean,RehabilitationTaskRecordQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationTaskRecordQuery param);

	/**
	 * 根据RecordId查询对象
	 */
	RehabilitationTaskRecord getRehabilitationTaskRecordByRecordId(String recordId);


	/**
	 * 根据RecordId修改
	 */
	Integer updateRehabilitationTaskRecordByRecordId(RehabilitationTaskRecord bean,String recordId);


	/**
	 * 根据RecordId删除
	 */
	Integer deleteRehabilitationTaskRecordByRecordId(String recordId);


	/**
	 * 根据PlanIdAndTrainingDate查询对象
	 */
	RehabilitationTaskRecord getRehabilitationTaskRecordByPlanIdAndTrainingDate(String planId, Date trainingDate);


	/**
	 * 根据PlanIdAndTrainingDate修改
	 */
	Integer updateRehabilitationTaskRecordByPlanIdAndTrainingDate(RehabilitationTaskRecord bean,String planId,Date trainingDate);


	/**
	 * 根据PlanIdAndTrainingDate删除
	 */
	Integer deleteRehabilitationTaskRecordByPlanIdAndTrainingDate(String planId,Date trainingDate);

}