package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.manage.entity.query.RehabilitationTypeQuery;
import com.lin.hr.manage.entity.po.RehabilitationType;


/**
 * 康复类型表 业务接口
 */
public interface RehabilitationTypeService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationType> findListByParam(RehabilitationTypeQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationTypeQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationType> findListByPage(RehabilitationTypeQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationType bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationType> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationType> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationType bean,RehabilitationTypeQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationTypeQuery param);

	/**
	 * 根据TypeId查询对象
	 */
	RehabilitationType getRehabilitationTypeByTypeId(String typeId);


	/**
	 * 根据TypeId修改
	 */
	Integer updateRehabilitationTypeByTypeId(RehabilitationType bean,String typeId);


	/**
	 * 根据TypeId删除
	 */
	Integer deleteRehabilitationTypeByTypeId(String typeId);


	/**
	 * 根据TypeCode查询对象
	 */
	RehabilitationType getRehabilitationTypeByTypeCode(String typeCode);


	/**
	 * 根据TypeCode修改
	 */
	Integer updateRehabilitationTypeByTypeCode(RehabilitationType bean,String typeCode);


	/**
	 * 根据TypeCode删除
	 */
	Integer deleteRehabilitationTypeByTypeCode(String typeCode);

}