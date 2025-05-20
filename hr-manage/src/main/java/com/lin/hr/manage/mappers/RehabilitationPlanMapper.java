package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 个性化康复计划表：康复师为患者制定的方案 数据库操作接口
 */
public interface RehabilitationPlanMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据PlanId更新
	 */
	 Integer updateByPlanId(@Param("bean") T t,@Param("planId") String planId);


	/**
	 * 根据PlanId删除
	 */
	 Integer deleteByPlanId(@Param("planId") String planId);


	/**
	 * 根据PlanId获取对象
	 */
	 T selectByPlanId(@Param("planId") String planId);


}
