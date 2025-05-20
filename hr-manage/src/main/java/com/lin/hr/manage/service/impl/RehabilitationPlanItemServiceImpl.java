package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.manage.entity.query.SimplePage;
import org.springframework.stereotype.Service;

import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;

import com.lin.hr.manage.entity.query.RehabilitationPlanItemQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlanItem;
import com.lin.hr.manage.mappers.RehabilitationPlanItemMapper;
import com.lin.hr.manage.service.RehabilitationPlanItemService;


/**
 * 康复计划明细项（个性化） 业务接口实现
 */
@Service("rehabilitationPlanItemService")
public class RehabilitationPlanItemServiceImpl implements RehabilitationPlanItemService {

	@Resource
	private RehabilitationPlanItemMapper<RehabilitationPlanItem, RehabilitationPlanItemQuery> rehabilitationPlanItemMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<RehabilitationPlanItem> findListByParam(RehabilitationPlanItemQuery param) {
		return this.rehabilitationPlanItemMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(RehabilitationPlanItemQuery param) {
		return this.rehabilitationPlanItemMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<RehabilitationPlanItem> findListByPage(RehabilitationPlanItemQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<RehabilitationPlanItem> list = this.findListByParam(param);
		PaginationResultVO<RehabilitationPlanItem> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(RehabilitationPlanItem bean) {
		return this.rehabilitationPlanItemMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<RehabilitationPlanItem> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationPlanItemMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<RehabilitationPlanItem> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationPlanItemMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(RehabilitationPlanItem bean, RehabilitationPlanItemQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationPlanItemMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(RehabilitationPlanItemQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationPlanItemMapper.deleteByParam(param);
	}

	/**
	 * 根据DetailId获取对象
	 */
	@Override
	public RehabilitationPlanItem getRehabilitationPlanItemByDetailId(String detailId) {
		return this.rehabilitationPlanItemMapper.selectByDetailId(detailId);
	}

	/**
	 * 根据DetailId修改
	 */
	@Override
	public Integer updateRehabilitationPlanItemByDetailId(RehabilitationPlanItem bean, String detailId) {
		return this.rehabilitationPlanItemMapper.updateByDetailId(bean, detailId);
	}

	/**
	 * 根据DetailId删除
	 */
	@Override
	public Integer deleteRehabilitationPlanItemByDetailId(String detailId) {
		return this.rehabilitationPlanItemMapper.deleteByDetailId(detailId);
	}
}