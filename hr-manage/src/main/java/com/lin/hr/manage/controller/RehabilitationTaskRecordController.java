package com.lin.hr.manage.controller;

import java.util.Date;
import java.util.List;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.RehabilitationTaskRecordQuery;
import com.lin.hr.manage.entity.po.RehabilitationTaskRecord;
import com.lin.hr.manage.service.RehabilitationTaskRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 每日训练记录表：患者每日打卡与反馈 Controller
 */
@RestController("rehabilitationTaskRecordController")
@RequestMapping("/rehabilitationTaskRecord")
public class RehabilitationTaskRecordController extends ABaseController {

	@Resource
	private RehabilitationTaskRecordService rehabilitationTaskRecordService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	@GlobalInterceptor
	public ResponseVO loadDataList(RehabilitationTaskRecordQuery query){
		return getSuccessResponseVO(rehabilitationTaskRecordService.findListByPage(query));
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<RehabilitationTaskRecord> listBean) {
		rehabilitationTaskRecordService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationTaskRecord> listBean) {
		rehabilitationTaskRecordService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据RecordId查询对象
	 */
	@RequestMapping("/getRehabilitationTaskRecordByRecordId")
	public ResponseVO getRehabilitationTaskRecordByRecordId(String recordId) {
		return getSuccessResponseVO(rehabilitationTaskRecordService.getRehabilitationTaskRecordByRecordId(recordId));
	}

	/**
	 * 根据RecordId修改对象
	 */
	@RequestMapping("/updateRehabilitationTaskRecordByRecordId")
	public ResponseVO updateRehabilitationTaskRecordByRecordId(RehabilitationTaskRecord bean,String recordId) {
		rehabilitationTaskRecordService.updateRehabilitationTaskRecordByRecordId(bean,recordId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据RecordId删除
	 */
	@RequestMapping("/deleteRehabilitationTaskRecordByRecordId")
	public ResponseVO deleteRehabilitationTaskRecordByRecordId(String recordId) {
		rehabilitationTaskRecordService.deleteRehabilitationTaskRecordByRecordId(recordId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据PlanIdAndTrainingDate修改对象
	 */
	@RequestMapping("/updateRehabilitationTaskRecordByPlanIdAndTrainingDate")
	public ResponseVO updateRehabilitationTaskRecordByPlanIdAndTrainingDate(RehabilitationTaskRecord bean,String planId,Date trainingDate) {
		rehabilitationTaskRecordService.updateRehabilitationTaskRecordByPlanIdAndTrainingDate(bean,planId,trainingDate);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据PlanIdAndTrainingDate删除
	 */
	@RequestMapping("/deleteRehabilitationTaskRecordByPlanIdAndTrainingDate")
	public ResponseVO deleteRehabilitationTaskRecordByPlanIdAndTrainingDate(String planId, Date trainingDate) {
		rehabilitationTaskRecordService.deleteRehabilitationTaskRecordByPlanIdAndTrainingDate(planId,trainingDate);
		return getSuccessResponseVO(null);
	}
	// 以下是用的接口
	/**
	 * 新增
	 */
	@PostMapping("/add")
	@GlobalInterceptor
	public ResponseVO<Object> add(@RequestBody RehabilitationTaskRecord bean) {
		rehabilitationTaskRecordService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据PlanIdAndTrainingDate查询对象
	 */
	@PostMapping("/getRecordByPlanIdAndDate")
	@GlobalInterceptor
	public ResponseVO<Object> getRecordByPlanIdAndDate(String planId, Date trainingDate) {
		return getSuccessResponseVO(rehabilitationTaskRecordService.getRehabilitationTaskRecordByPlanIdAndTrainingDate(planId,trainingDate));
	}
}