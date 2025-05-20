package com.lin.hr.manage.service;

import java.util.List;

import com.lin.hr.manage.entity.query.RehabilitationTemplateItemQuery;
import com.lin.hr.manage.entity.po.RehabilitationTemplateItem;
import com.lin.hr.common.vo.PaginationResultVO;


/**
 * 康复模板明细项 业务接口
 */
public interface RehabilitationTemplateItemService {

	/**
	 * 根据条件查询列表
	 */
	List<RehabilitationTemplateItem> findListByParam(RehabilitationTemplateItemQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(RehabilitationTemplateItemQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<RehabilitationTemplateItem> findListByPage(RehabilitationTemplateItemQuery param);

	/**
	 * 新增
	 */
	Integer add(RehabilitationTemplateItem bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<RehabilitationTemplateItem> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<RehabilitationTemplateItem> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(RehabilitationTemplateItem bean,RehabilitationTemplateItemQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(RehabilitationTemplateItemQuery param);

	/**
	 * 根据Id查询对象
	 */
	RehabilitationTemplateItem getRehabilitationTemplateItemById(String id);


	/**
	 * 根据Id修改
	 */
	Integer updateRehabilitationTemplateItemById(RehabilitationTemplateItem bean,String id);


	/**
	 * 根据Id删除
	 */
	Integer deleteRehabilitationTemplateItemById(String id);

}