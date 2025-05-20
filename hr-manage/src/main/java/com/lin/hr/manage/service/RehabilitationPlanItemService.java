package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.manage.entity.query.RehabilitationPlanItemQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlanItem;
import com.lin.hr.common.vo.PaginationResultVO;


/**
 * 康复计划明细项（个性化） 业务接口
 */
public interface RehabilitationPlanItemService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationPlanItem> findListByParam(RehabilitationPlanItemQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationPlanItemQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationPlanItem> findListByPage(RehabilitationPlanItemQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationPlanItem bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationPlanItem> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationPlanItem> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationPlanItem bean,RehabilitationPlanItemQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationPlanItemQuery param);

	/**
	 * 根据DetailId查询对象
	 */
	RehabilitationPlanItem getRehabilitationPlanItemByDetailId(String detailId);


	/**
	 * 根据DetailId修改
	 */
	Integer updateRehabilitationPlanItemByDetailId(RehabilitationPlanItem bean,String detailId);


	/**
	 * 根据DetailId删除
	 */
	Integer deleteRehabilitationPlanItemByDetailId(String detailId);

}