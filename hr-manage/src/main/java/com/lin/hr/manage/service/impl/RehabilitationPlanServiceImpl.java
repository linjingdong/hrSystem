package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.manage.entity.query.SimplePage;
import org.springframework.stereotype.Service;


import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;

import com.lin.hr.manage.entity.query.RehabilitationPlanQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlan;
import com.lin.hr.manage.mappers.RehabilitationPlanMapper;
import com.lin.hr.manage.service.RehabilitationPlanService;


/**
 * 个性化康复计划表：康复师为患者制定的方案 业务接口实现
 */
@Service("rehabilitationPlanService")
public class RehabilitationPlanServiceImpl implements RehabilitationPlanService {

	@Resource
	private RehabilitationPlanMapper<RehabilitationPlan, RehabilitationPlanQuery> rehabilitationPlanMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<RehabilitationPlan> findListByParam(RehabilitationPlanQuery param) {
		return this.rehabilitationPlanMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(RehabilitationPlanQuery param) {
		return this.rehabilitationPlanMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<RehabilitationPlan> findListByPage(RehabilitationPlanQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<RehabilitationPlan> list = this.findListByParam(param);
		PaginationResultVO<RehabilitationPlan> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(RehabilitationPlan bean) {
		return this.rehabilitationPlanMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<RehabilitationPlan> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationPlanMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<RehabilitationPlan> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationPlanMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(RehabilitationPlan bean, RehabilitationPlanQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationPlanMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(RehabilitationPlanQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationPlanMapper.deleteByParam(param);
	}

	/**
	 * 根据PlanId获取对象
	 */
	@Override
	public RehabilitationPlan getRehabilitationPlanByPlanId(String planId) {
		return this.rehabilitationPlanMapper.selectByPlanId(planId);
	}

	/**
	 * 根据PlanId修改
	 */
	@Override
	public Integer updateRehabilitationPlanByPlanId(RehabilitationPlan bean, String planId) {
		return this.rehabilitationPlanMapper.updateByPlanId(bean, planId);
	}

	/**
	 * 根据PlanId删除
	 */
	@Override
	public Integer deleteRehabilitationPlanByPlanId(String planId) {
		return this.rehabilitationPlanMapper.deleteByPlanId(planId);
	}
}