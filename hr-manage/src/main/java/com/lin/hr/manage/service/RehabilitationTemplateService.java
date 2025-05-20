package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.manage.entity.dto.RehabilitationTemplateDto;
import com.lin.hr.manage.entity.query.RehabilitationTemplateQuery;
import com.lin.hr.manage.entity.po.RehabilitationTemplate;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.manage.entity.vo.RehabilitationTemplateVo;


/**
 * 康复训练模板表：后台预设标准训练方案 业务接口
 */
public interface RehabilitationTemplateService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationTemplate> findListByParam(RehabilitationTemplateQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationTemplateQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationTemplate> findListByPage(RehabilitationTemplateQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationTemplate bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationTemplate> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationTemplate> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationTemplate bean,RehabilitationTemplateQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationTemplateQuery param);

	/**
	 * 根据TemplateId查询对象
	 */
	RehabilitationTemplate getRehabilitationTemplateByTemplateId(String templateId);


	/**
	 * 根据TemplateId修改
	 */
	Integer updateRehabilitationTemplateByTemplateId(RehabilitationTemplate bean,String templateId);


	/**
	 * 根据TemplateId删除
	 */
	Integer deleteRehabilitationTemplateByTemplateId(String templateId);

	/**
	 * 创建模板
	 * @param dto dto
	 */
	void createTemplate(RehabilitationTemplateDto dto);

	void deleteById(String templateId);

	RehabilitationTemplateVo getTemplateById(String templateId);

	void updateById(String templateId, RehabilitationTemplateDto dto);
}