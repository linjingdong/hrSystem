package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 康复师信息表：存储康复师专业资料 数据库操作接口
 */
public interface RehabilitationTherapistMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据TherapistId更新
	 */
	 Integer updateByTherapistId(@Param("bean") T t,@Param("therapistId") String therapistId);


	/**
	 * 根据TherapistId删除
	 */
	 Integer deleteByTherapistId(@Param("therapistId") String therapistId);


	/**
	 * 根据TherapistId获取对象
	 */
	 T selectByTherapistId(@Param("therapistId") String therapistId);


}
