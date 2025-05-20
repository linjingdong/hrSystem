package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.manage.entity.query.RehabilitationTherapistQuery;
import com.lin.hr.manage.entity.po.RehabilitationTherapist;
import com.lin.hr.common.vo.PaginationResultVO;


/**
 * 康复师信息表：存储康复师专业资料 业务接口
 */
public interface RehabilitationTherapistService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationTherapist> findListByParam(RehabilitationTherapistQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationTherapistQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationTherapist> findListByPage(RehabilitationTherapistQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationTherapist bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationTherapist> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationTherapist> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationTherapist bean,RehabilitationTherapistQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationTherapistQuery param);

	/**
	 * 根据TherapistId查询对象
	 */
	RehabilitationTherapist getRehabilitationTherapistByTherapistId(String therapistId);


	/**
	 * 根据TherapistId修改
	 */
	Integer updateRehabilitationTherapistByTherapistId(RehabilitationTherapist bean,String therapistId);


	/**
	 * 根据TherapistId删除
	 */
	Integer deleteRehabilitationTherapistByTherapistId(String therapistId);

}