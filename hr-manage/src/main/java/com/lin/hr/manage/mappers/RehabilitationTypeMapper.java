package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 康复类型表 数据库操作接口
 */
public interface RehabilitationTypeMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据TypeId更新
	 */
	 Integer updateByTypeId(@Param("bean") T t,@Param("typeId") String typeId);


	/**
	 * 根据TypeId删除
	 */
	 Integer deleteByTypeId(@Param("typeId") String typeId);


	/**
	 * 根据TypeId获取对象
	 */
	 T selectByTypeId(@Param("typeId") String typeId);


	/**
	 * 根据TypeCode更新
	 */
	 Integer updateByTypeCode(@Param("bean") T t,@Param("typeCode") String typeCode);


	/**
	 * 根据TypeCode删除
	 */
	 Integer deleteByTypeCode(@Param("typeCode") String typeCode);


	/**
	 * 根据TypeCode获取对象
	 */
	 T selectByTypeCode(@Param("typeCode") String typeCode);


}
