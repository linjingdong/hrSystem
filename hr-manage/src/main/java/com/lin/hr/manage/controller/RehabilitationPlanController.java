package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.RehabilitationPlanQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlan;

import com.lin.hr.manage.service.RehabilitationPlanService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 个性化康复计划表：康复师为患者制定的方案 Controller
 */
@RestController("rehabilitationPlanController")
@RequestMapping("/rehabilitationPlan")
public class RehabilitationPlanController extends ABaseController {

	@Resource
	private RehabilitationPlanService rehabilitationPlanService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(RehabilitationPlanQuery query){
		return getSuccessResponseVO(rehabilitationPlanService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(RehabilitationPlan bean) {
		rehabilitationPlanService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<RehabilitationPlan> listBean) {
		rehabilitationPlanService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationPlan> listBean) {
		rehabilitationPlanService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据PlanId查询对象
	 */
	@RequestMapping("/getRehabilitationPlanByPlanId")
	public ResponseVO getRehabilitationPlanByPlanId(String planId) {
		return getSuccessResponseVO(rehabilitationPlanService.getRehabilitationPlanByPlanId(planId));
	}

	/**
	 * 根据PlanId修改对象
	 */
	@RequestMapping("/updateRehabilitationPlanByPlanId")
	public ResponseVO updateRehabilitationPlanByPlanId(RehabilitationPlan bean,String planId) {
		rehabilitationPlanService.updateRehabilitationPlanByPlanId(bean,planId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据PlanId删除
	 */
	@RequestMapping("/deleteRehabilitationPlanByPlanId")
	public ResponseVO deleteRehabilitationPlanByPlanId(String planId) {
		rehabilitationPlanService.deleteRehabilitationPlanByPlanId(planId);
		return getSuccessResponseVO(null);
	}
}