package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.dto.RehabilitationPlanDto;
import com.lin.hr.manage.entity.query.RehabilitationPlanQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlan;

import com.lin.hr.manage.service.RehabilitationPlanService;
import org.springframework.web.bind.annotation.PostMapping;
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

	/**
	 * 根据条件分页查询
	 */
	@PostMapping("/loadDataList")
	@GlobalInterceptor
	public ResponseVO loadDataList(RehabilitationPlanQuery query){
		return getSuccessResponseVO(rehabilitationPlanService.findListByPage(query));
	}

	/**
	 * 创建康复计划
	 */
	@PostMapping("/createPlan")
	@GlobalInterceptor
	public ResponseVO<Object> createPlan(@RequestBody RehabilitationPlanDto dto) {
		TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
		rehabilitationPlanService.createPlan(dto,tokenUserInfo);
		return getSuccessResponseVO("创建成功");
	}

	/**
	 * 更新康复计划列表
	 */
	@PostMapping("/updatePlan")
	@GlobalInterceptor
	public ResponseVO<Object> updatePlan(@RequestBody RehabilitationPlanDto dto) {
		rehabilitationPlanService.updatePlan(dto);
		return getSuccessResponseVO("修改成功");
	}

	/**
	 * 通过planId获取康复计划详情
	 */
	@PostMapping("/getByPlanId")
	@GlobalInterceptor
	public ResponseVO<Object> getPlanByPlanId(String planId) {
		RehabilitationPlanDto dto = rehabilitationPlanService.getByPlanId(planId);
		return getSuccessResponseVO(dto);
	}
}