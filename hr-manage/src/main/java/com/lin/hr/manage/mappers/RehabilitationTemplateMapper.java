package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 康复训练模板表：后台预设标准训练方案 数据库操作接口
 */
public interface RehabilitationTemplateMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据TemplateId更新
	 */
	 Integer updateByTemplateId(@Param("bean") T t,@Param("templateId") String templateId);


	/**
	 * 根据TemplateId删除
	 */
	 Integer deleteByTemplateId(@Param("templateId") String templateId);


	/**
	 * 根据TemplateId获取对象
	 */
	 T selectByTemplateId(@Param("templateId") String templateId);


}
