package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.manage.entity.query.RehabilitationFeedbackQuery;
import com.lin.hr.manage.entity.po.RehabilitationFeedback;


/**
 * 康复计划反馈表：患者对完成计划的评分与评价 业务接口
 */
public interface RehabilitationFeedbackService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationFeedback> findListByParam(RehabilitationFeedbackQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationFeedbackQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationFeedback> findListByPage(RehabilitationFeedbackQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationFeedback bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationFeedback> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationFeedback> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationFeedback bean,RehabilitationFeedbackQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationFeedbackQuery param);

	/**
	 * 根据FeedbackId查询对象
	 */
	RehabilitationFeedback getRehabilitationFeedbackByFeedbackId(String feedbackId);


	/**
	 * 根据FeedbackId修改
	 */
	Integer updateRehabilitationFeedbackByFeedbackId(RehabilitationFeedback bean,String feedbackId);


	/**
	 * 根据FeedbackId删除
	 */
	Integer deleteRehabilitationFeedbackByFeedbackId(String feedbackId);

}