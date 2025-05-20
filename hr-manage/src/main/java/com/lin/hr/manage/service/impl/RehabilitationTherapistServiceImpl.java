package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.manage.entity.query.SimplePage;
import org.springframework.stereotype.Service;

import com.lin.hr.manage.entity.query.RehabilitationTherapistQuery;
import com.lin.hr.manage.entity.po.RehabilitationTherapist;
import com.lin.hr.manage.mappers.RehabilitationTherapistMapper;
import com.lin.hr.manage.service.RehabilitationTherapistService;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;


/**
 * 康复师信息表：存储康复师专业资料 业务接口实现
 */
@Service("rehabilitationTherapistService")
public class RehabilitationTherapistServiceImpl implements RehabilitationTherapistService {

	@Resource
	private RehabilitationTherapistMapper<RehabilitationTherapist, RehabilitationTherapistQuery> rehabilitationTherapistMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<RehabilitationTherapist> findListByParam(RehabilitationTherapistQuery param) {
		return this.rehabilitationTherapistMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(RehabilitationTherapistQuery param) {
		return this.rehabilitationTherapistMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<RehabilitationTherapist> findListByPage(RehabilitationTherapistQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<RehabilitationTherapist> list = this.findListByParam(param);
		PaginationResultVO<RehabilitationTherapist> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(RehabilitationTherapist bean) {
		return this.rehabilitationTherapistMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<RehabilitationTherapist> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationTherapistMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<RehabilitationTherapist> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationTherapistMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(RehabilitationTherapist bean, RehabilitationTherapistQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationTherapistMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(RehabilitationTherapistQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationTherapistMapper.deleteByParam(param);
	}

	/**
	 * 根据TherapistId获取对象
	 */
	@Override
	public RehabilitationTherapist getRehabilitationTherapistByTherapistId(String therapistId) {
		return this.rehabilitationTherapistMapper.selectByTherapistId(therapistId);
	}

	/**
	 * 根据TherapistId修改
	 */
	@Override
	public Integer updateRehabilitationTherapistByTherapistId(RehabilitationTherapist bean, String therapistId) {
		return this.rehabilitationTherapistMapper.updateByTherapistId(bean, therapistId);
	}

	/**
	 * 根据TherapistId删除
	 */
	@Override
	public Integer deleteRehabilitationTherapistByTherapistId(String therapistId) {
		return this.rehabilitationTherapistMapper.deleteByTherapistId(therapistId);
	}
}