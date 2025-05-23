package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.utils.SnowflakeIdGenerator;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.RehabilitationTypeQuery;
import com.lin.hr.manage.entity.po.RehabilitationType;

import com.lin.hr.manage.service.RehabilitationTypeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复类型表 Controller
 */
@RestController("rehabilitationTypeController")
@RequestMapping("/rehabilitationType")
public class RehabilitationTypeController extends ABaseController {

	@Resource
	private RehabilitationTypeService rehabilitationTypeService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(RehabilitationTypeQuery query){
		return getSuccessResponseVO(rehabilitationTypeService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(RehabilitationType bean) {
		SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
		bean.setTypeId(String.valueOf(snowflakeIdGenerator.nextId()));
		rehabilitationTypeService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<RehabilitationType> listBean) {
		rehabilitationTypeService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationType> listBean) {
		rehabilitationTypeService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据TypeId查询对象
	 */
	@RequestMapping("/getRehabilitationTypeByTypeId")
	public ResponseVO getRehabilitationTypeByTypeId(String typeId) {
		return getSuccessResponseVO(rehabilitationTypeService.getRehabilitationTypeByTypeId(typeId));
	}

	/**
	 * 根据TypeId修改对象
	 */
	@RequestMapping("/updateRehabilitationTypeByTypeId")
	public ResponseVO updateRehabilitationTypeByTypeId(RehabilitationType bean,String typeId) {
		rehabilitationTypeService.updateRehabilitationTypeByTypeId(bean,typeId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据TypeId删除
	 */
	@RequestMapping("/deleteRehabilitationTypeByTypeId")
	public ResponseVO deleteRehabilitationTypeByTypeId(String typeId) {
		rehabilitationTypeService.deleteRehabilitationTypeByTypeId(typeId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据TypeCode查询对象
	 */
	@RequestMapping("/getRehabilitationTypeByTypeCode")
	public ResponseVO getRehabilitationTypeByTypeCode(String typeCode) {
		return getSuccessResponseVO(rehabilitationTypeService.getRehabilitationTypeByTypeCode(typeCode));
	}

	/**
	 * 根据TypeCode修改对象
	 */
	@RequestMapping("/updateRehabilitationTypeByTypeCode")
	public ResponseVO updateRehabilitationTypeByTypeCode(RehabilitationType bean,String typeCode) {
		rehabilitationTypeService.updateRehabilitationTypeByTypeCode(bean,typeCode);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据TypeCode删除
	 */
	@RequestMapping("/deleteRehabilitationTypeByTypeCode")
	public ResponseVO deleteRehabilitationTypeByTypeCode(String typeCode) {
		rehabilitationTypeService.deleteRehabilitationTypeByTypeCode(typeCode);
		return getSuccessResponseVO(null);
	}
}