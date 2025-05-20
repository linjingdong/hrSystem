package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.RehabilitationTemplateItemQuery;
import com.lin.hr.manage.entity.po.RehabilitationTemplateItem;
import com.lin.hr.manage.service.RehabilitationTemplateItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复模板明细项 Controller
 */
@RestController("rehabilitationTemplateItemController")
@RequestMapping("/rehabilitationTemplateItem")
public class RehabilitationTemplateItemController extends ABaseController {

	@Resource
	private RehabilitationTemplateItemService rehabilitationTemplateItemService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(RehabilitationTemplateItemQuery query){
		return getSuccessResponseVO(rehabilitationTemplateItemService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(RehabilitationTemplateItem bean) {
		rehabilitationTemplateItemService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<RehabilitationTemplateItem> listBean) {
		rehabilitationTemplateItemService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationTemplateItem> listBean) {
		rehabilitationTemplateItemService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id查询对象
	 */
	@RequestMapping("/getRehabilitationTemplateItemById")
	public ResponseVO getRehabilitationTemplateItemById(String id) {
		return getSuccessResponseVO(rehabilitationTemplateItemService.getRehabilitationTemplateItemById(id));
	}

	/**
	 * 根据Id修改对象
	 */
	@RequestMapping("/updateRehabilitationTemplateItemById")
	public ResponseVO updateRehabilitationTemplateItemById(RehabilitationTemplateItem bean,String id) {
		rehabilitationTemplateItemService.updateRehabilitationTemplateItemById(bean,id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("/deleteRehabilitationTemplateItemById")
	public ResponseVO deleteRehabilitationTemplateItemById(String id) {
		rehabilitationTemplateItemService.deleteRehabilitationTemplateItemById(id);
		return getSuccessResponseVO(null);
	}
}