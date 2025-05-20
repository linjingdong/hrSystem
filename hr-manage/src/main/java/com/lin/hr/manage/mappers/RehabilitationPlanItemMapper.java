package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 康复计划明细项（个性化） 数据库操作接口
 */
public interface RehabilitationPlanItemMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据DetailId更新
	 */
	 Integer updateByDetailId(@Param("bean") T t,@Param("detailId") String detailId);


	/**
	 * 根据DetailId删除
	 */
	 Integer deleteByDetailId(@Param("detailId") String detailId);


	/**
	 * 根据DetailId获取对象
	 */
	 T selectByDetailId(@Param("detailId") String detailId);


}
