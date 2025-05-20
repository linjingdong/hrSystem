package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.RehabilitationAssignmentQuery;
import com.lin.hr.manage.entity.po.RehabilitationAssignment;
import com.lin.hr.manage.service.RehabilitationAssignmentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复师-患者绑定表：管理多对多指导关系 Controller
 */
@RestController("rehabilitationAssignmentController")
@RequestMapping("/rehabilitationAssignment")
public class RehabilitationAssignmentController extends ABaseController {

	@Resource
	private RehabilitationAssignmentService rehabilitationAssignmentService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(RehabilitationAssignmentQuery query){
		return getSuccessResponseVO(rehabilitationAssignmentService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(RehabilitationAssignment bean) {
		rehabilitationAssignmentService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<RehabilitationAssignment> listBean) {
		rehabilitationAssignmentService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationAssignment> listBean) {
		rehabilitationAssignmentService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据AssignmentId查询对象
	 */
	@RequestMapping("/getRehabilitationAssignmentByAssignmentId")
	public ResponseVO getRehabilitationAssignmentByAssignmentId(String assignmentId) {
		return getSuccessResponseVO(rehabilitationAssignmentService.getRehabilitationAssignmentByAssignmentId(assignmentId));
	}

	/**
	 * 根据AssignmentId修改对象
	 */
	@RequestMapping("/updateRehabilitationAssignmentByAssignmentId")
	public ResponseVO updateRehabilitationAssignmentByAssignmentId(RehabilitationAssignment bean,String assignmentId) {
		rehabilitationAssignmentService.updateRehabilitationAssignmentByAssignmentId(bean,assignmentId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据AssignmentId删除
	 */
	@RequestMapping("/deleteRehabilitationAssignmentByAssignmentId")
	public ResponseVO deleteRehabilitationAssignmentByAssignmentId(String assignmentId) {
		rehabilitationAssignmentService.deleteRehabilitationAssignmentByAssignmentId(assignmentId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据TherapistIdAndUserId查询对象
	 */
	@RequestMapping("/getRehabilitationAssignmentByTherapistIdAndUserId")
	public ResponseVO getRehabilitationAssignmentByTherapistIdAndUserId(String therapistId,String userId) {
		return getSuccessResponseVO(rehabilitationAssignmentService.getRehabilitationAssignmentByTherapistIdAndUserId(therapistId,userId));
	}

	/**
	 * 根据TherapistIdAndUserId修改对象
	 */
	@RequestMapping("/updateRehabilitationAssignmentByTherapistIdAndUserId")
	public ResponseVO updateRehabilitationAssignmentByTherapistIdAndUserId(RehabilitationAssignment bean,String therapistId,String userId) {
		rehabilitationAssignmentService.updateRehabilitationAssignmentByTherapistIdAndUserId(bean,therapistId,userId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据TherapistIdAndUserId删除
	 */
	@RequestMapping("/deleteRehabilitationAssignmentByTherapistIdAndUserId")
	public ResponseVO deleteRehabilitationAssignmentByTherapistIdAndUserId(String therapistId,String userId) {
		rehabilitationAssignmentService.deleteRehabilitationAssignmentByTherapistIdAndUserId(therapistId,userId);
		return getSuccessResponseVO(null);
	}
}