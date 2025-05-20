package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.RehabilitationPlanItemQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlanItem;
import com.lin.hr.manage.service.RehabilitationPlanItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复计划明细项（个性化） Controller
 */
@RestController("rehabilitationPlanItemController")
@RequestMapping("/rehabilitationPlanItem")
public class RehabilitationPlanItemController extends ABaseController {

	@Resource
	private RehabilitationPlanItemService rehabilitationPlanItemService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(RehabilitationPlanItemQuery query){
		return getSuccessResponseVO(rehabilitationPlanItemService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(RehabilitationPlanItem bean) {
		rehabilitationPlanItemService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<RehabilitationPlanItem> listBean) {
		rehabilitationPlanItemService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationPlanItem> listBean) {
		rehabilitationPlanItemService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据DetailId查询对象
	 */
	@RequestMapping("/getRehabilitationPlanItemByDetailId")
	public ResponseVO getRehabilitationPlanItemByDetailId(String detailId) {
		return getSuccessResponseVO(rehabilitationPlanItemService.getRehabilitationPlanItemByDetailId(detailId));
	}

	/**
	 * 根据DetailId修改对象
	 */
	@RequestMapping("/updateRehabilitationPlanItemByDetailId")
	public ResponseVO updateRehabilitationPlanItemByDetailId(RehabilitationPlanItem bean,String detailId) {
		rehabilitationPlanItemService.updateRehabilitationPlanItemByDetailId(bean,detailId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据DetailId删除
	 */
	@RequestMapping("/deleteRehabilitationPlanItemByDetailId")
	public ResponseVO deleteRehabilitationPlanItemByDetailId(String detailId) {
		rehabilitationPlanItemService.deleteRehabilitationPlanItemByDetailId(detailId);
		return getSuccessResponseVO(null);
	}
}