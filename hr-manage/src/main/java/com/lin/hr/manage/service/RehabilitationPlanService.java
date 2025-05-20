package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.manage.entity.query.RehabilitationPlanQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlan;
import com.lin.hr.common.vo.PaginationResultVO;


/**
 * 个性化康复计划表：康复师为患者制定的方案 业务接口
 */
public interface RehabilitationPlanService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationPlan> findListByParam(RehabilitationPlanQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationPlanQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationPlan> findListByPage(RehabilitationPlanQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationPlan bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationPlan> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationPlan> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationPlan bean,RehabilitationPlanQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationPlanQuery param);

	/**
	 * 根据PlanId查询对象
	 */
	RehabilitationPlan getRehabilitationPlanByPlanId(String planId);


	/**
	 * 根据PlanId修改
	 */
	Integer updateRehabilitationPlanByPlanId(RehabilitationPlan bean,String planId);


	/**
	 * 根据PlanId删除
	 */
	Integer deleteRehabilitationPlanByPlanId(String planId);

}