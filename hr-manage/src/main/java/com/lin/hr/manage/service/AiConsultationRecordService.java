package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.manage.entity.query.AiConsultationRecordQuery;
import com.lin.hr.manage.entity.po.AiConsultationRecord;


/**
 *  业务接口
 */
public interface AiConsultationRecordService {

	/**
	 * 根据条件查询列表
	 */
	List<AiConsultationRecord> findListByParam(AiConsultationRecordQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(AiConsultationRecordQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<AiConsultationRecord> findListByPage(AiConsultationRecordQuery param);

	/**
	 * 新增
	 */
	Integer add(AiConsultationRecord bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<AiConsultationRecord> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<AiConsultationRecord> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(AiConsultationRecord bean,AiConsultationRecordQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(AiConsultationRecordQuery param);

	/**
	 * 根据ConsultationId查询对象
	 */
	AiConsultationRecord getAiConsultationRecordByConsultationId(String consultationId);


	/**
	 * 根据ConsultationId修改
	 */
	Integer updateAiConsultationRecordByConsultationId(AiConsultationRecord bean,String consultationId);


	/**
	 * 根据ConsultationId删除
	 */
	Integer deleteAiConsultationRecordByConsultationId(String consultationId);

}