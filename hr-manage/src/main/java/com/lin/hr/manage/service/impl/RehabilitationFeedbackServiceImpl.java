package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.manage.entity.query.SimplePage;
import org.springframework.stereotype.Service;

import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;

import com.lin.hr.manage.entity.query.RehabilitationFeedbackQuery;
import com.lin.hr.manage.entity.po.RehabilitationFeedback;
import com.lin.hr.manage.mappers.RehabilitationFeedbackMapper;
import com.lin.hr.manage.service.RehabilitationFeedbackService;


/**
 * 康复计划反馈表：患者对完成计划的评分与评价 业务接口实现
 */
@Service("rehabilitationFeedbackService")
public class RehabilitationFeedbackServiceImpl implements RehabilitationFeedbackService {

	@Resource
	private RehabilitationFeedbackMapper<RehabilitationFeedback, RehabilitationFeedbackQuery> rehabilitationFeedbackMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<RehabilitationFeedback> findListByParam(RehabilitationFeedbackQuery param) {
		return this.rehabilitationFeedbackMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(RehabilitationFeedbackQuery param) {
		return this.rehabilitationFeedbackMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<RehabilitationFeedback> findListByPage(RehabilitationFeedbackQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<RehabilitationFeedback> list = this.findListByParam(param);
		PaginationResultVO<RehabilitationFeedback> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(RehabilitationFeedback bean) {
		return this.rehabilitationFeedbackMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<RehabilitationFeedback> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationFeedbackMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<RehabilitationFeedback> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationFeedbackMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(RehabilitationFeedback bean, RehabilitationFeedbackQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationFeedbackMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(RehabilitationFeedbackQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationFeedbackMapper.deleteByParam(param);
	}

	/**
	 * 根据FeedbackId获取对象
	 */
	@Override
	public RehabilitationFeedback getRehabilitationFeedbackByFeedbackId(String feedbackId) {
		return this.rehabilitationFeedbackMapper.selectByFeedbackId(feedbackId);
	}

	/**
	 * 根据FeedbackId修改
	 */
	@Override
	public Integer updateRehabilitationFeedbackByFeedbackId(RehabilitationFeedback bean, String feedbackId) {
		return this.rehabilitationFeedbackMapper.updateByFeedbackId(bean, feedbackId);
	}

	/**
	 * 根据FeedbackId删除
	 */
	@Override
	public Integer deleteRehabilitationFeedbackByFeedbackId(String feedbackId) {
		return this.rehabilitationFeedbackMapper.deleteByFeedbackId(feedbackId);
	}
}