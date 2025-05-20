package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.RehabilitationFeedbackQuery;
import com.lin.hr.manage.entity.po.RehabilitationFeedback;

import com.lin.hr.manage.service.RehabilitationFeedbackService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复计划反馈表：患者对完成计划的评分与评价 Controller
 */
@RestController("rehabilitationFeedbackController")
@RequestMapping("/rehabilitationFeedback")
public class RehabilitationFeedbackController extends ABaseController {

	@Resource
	private RehabilitationFeedbackService rehabilitationFeedbackService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(RehabilitationFeedbackQuery query){
		return getSuccessResponseVO(rehabilitationFeedbackService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(RehabilitationFeedback bean) {
		rehabilitationFeedbackService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<RehabilitationFeedback> listBean) {
		rehabilitationFeedbackService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationFeedback> listBean) {
		rehabilitationFeedbackService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据FeedbackId查询对象
	 */
	@RequestMapping("/getRehabilitationFeedbackByFeedbackId")
	public ResponseVO getRehabilitationFeedbackByFeedbackId(String feedbackId) {
		return getSuccessResponseVO(rehabilitationFeedbackService.getRehabilitationFeedbackByFeedbackId(feedbackId));
	}

	/**
	 * 根据FeedbackId修改对象
	 */
	@RequestMapping("/updateRehabilitationFeedbackByFeedbackId")
	public ResponseVO updateRehabilitationFeedbackByFeedbackId(RehabilitationFeedback bean,String feedbackId) {
		rehabilitationFeedbackService.updateRehabilitationFeedbackByFeedbackId(bean,feedbackId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据FeedbackId删除
	 */
	@RequestMapping("/deleteRehabilitationFeedbackByFeedbackId")
	public ResponseVO deleteRehabilitationFeedbackByFeedbackId(String feedbackId) {
		rehabilitationFeedbackService.deleteRehabilitationFeedbackByFeedbackId(feedbackId);
		return getSuccessResponseVO(null);
	}
}