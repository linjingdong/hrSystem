package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.manage.entity.query.RehabilitationAssignmentQuery;
import com.lin.hr.manage.entity.po.RehabilitationAssignment;


/**
 * 康复师-患者绑定表：管理多对多指导关系 业务接口
 */
public interface RehabilitationAssignmentService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationAssignment> findListByParam(RehabilitationAssignmentQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationAssignmentQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationAssignment> findListByPage(RehabilitationAssignmentQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationAssignment bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationAssignment> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationAssignment> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationAssignment bean,RehabilitationAssignmentQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationAssignmentQuery param);

	/**
	 * 根据AssignmentId查询对象
	 */
	RehabilitationAssignment getRehabilitationAssignmentByAssignmentId(String assignmentId);


	/**
	 * 根据AssignmentId修改
	 */
	Integer updateRehabilitationAssignmentByAssignmentId(RehabilitationAssignment bean,String assignmentId);


	/**
	 * 根据AssignmentId删除
	 */
	Integer deleteRehabilitationAssignmentByAssignmentId(String assignmentId);


	/**
	 * 根据TherapistIdAndUserId查询对象
	 */
	RehabilitationAssignment getRehabilitationAssignmentByTherapistIdAndUserId(String therapistId,String userId);


	/**
	 * 根据TherapistIdAndUserId修改
	 */
	Integer updateRehabilitationAssignmentByTherapistIdAndUserId(RehabilitationAssignment bean,String therapistId,String userId);


	/**
	 * 根据TherapistIdAndUserId删除
	 */
	Integer deleteRehabilitationAssignmentByTherapistIdAndUserId(String therapistId,String userId);

}