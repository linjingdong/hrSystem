package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.manage.entity.query.SimplePage;
import org.springframework.stereotype.Service;


import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;

import com.lin.hr.manage.entity.query.RehabilitationAssignmentQuery;
import com.lin.hr.manage.entity.po.RehabilitationAssignment;
import com.lin.hr.manage.mappers.RehabilitationAssignmentMapper;
import com.lin.hr.manage.service.RehabilitationAssignmentService;


/**
 * 康复师-患者绑定表：管理多对多指导关系 业务接口实现
 */
@Service("rehabilitationAssignmentService")
public class RehabilitationAssignmentServiceImpl implements RehabilitationAssignmentService {

	@Resource
	private RehabilitationAssignmentMapper<RehabilitationAssignment, RehabilitationAssignmentQuery> rehabilitationAssignmentMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<RehabilitationAssignment> findListByParam(RehabilitationAssignmentQuery param) {
		return this.rehabilitationAssignmentMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(RehabilitationAssignmentQuery param) {
		return this.rehabilitationAssignmentMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<RehabilitationAssignment> findListByPage(RehabilitationAssignmentQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<RehabilitationAssignment> list = this.findListByParam(param);
		PaginationResultVO<RehabilitationAssignment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(RehabilitationAssignment bean) {
		return this.rehabilitationAssignmentMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<RehabilitationAssignment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationAssignmentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<RehabilitationAssignment> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationAssignmentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(RehabilitationAssignment bean, RehabilitationAssignmentQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationAssignmentMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(RehabilitationAssignmentQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationAssignmentMapper.deleteByParam(param);
	}

	/**
	 * 根据AssignmentId获取对象
	 */
	@Override
	public RehabilitationAssignment getRehabilitationAssignmentByAssignmentId(String assignmentId) {
		return this.rehabilitationAssignmentMapper.selectByAssignmentId(assignmentId);
	}

	/**
	 * 根据AssignmentId修改
	 */
	@Override
	public Integer updateRehabilitationAssignmentByAssignmentId(RehabilitationAssignment bean, String assignmentId) {
		return this.rehabilitationAssignmentMapper.updateByAssignmentId(bean, assignmentId);
	}

	/**
	 * 根据AssignmentId删除
	 */
	@Override
	public Integer deleteRehabilitationAssignmentByAssignmentId(String assignmentId) {
		return this.rehabilitationAssignmentMapper.deleteByAssignmentId(assignmentId);
	}

	/**
	 * 根据TherapistIdAndUserId获取对象
	 */
	@Override
	public RehabilitationAssignment getRehabilitationAssignmentByTherapistIdAndUserId(String therapistId, String userId) {
		return this.rehabilitationAssignmentMapper.selectByTherapistIdAndUserId(therapistId, userId);
	}

	/**
	 * 根据TherapistIdAndUserId修改
	 */
	@Override
	public Integer updateRehabilitationAssignmentByTherapistIdAndUserId(RehabilitationAssignment bean, String therapistId, String userId) {
		return this.rehabilitationAssignmentMapper.updateByTherapistIdAndUserId(bean, therapistId, userId);
	}

	/**
	 * 根据TherapistIdAndUserId删除
	 */
	@Override
	public Integer deleteRehabilitationAssignmentByTherapistIdAndUserId(String therapistId, String userId) {
		return this.rehabilitationAssignmentMapper.deleteByTherapistIdAndUserId(therapistId, userId);
	}
}