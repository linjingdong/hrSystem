package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 *  数据库操作接口
 */
public interface AiConsultationRecordMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据ConsultationId更新
	 */
	 Integer updateByConsultationId(@Param("bean") T t,@Param("consultationId") String consultationId);


	/**
	 * 根据ConsultationId删除
	 */
	 Integer deleteByConsultationId(@Param("consultationId") String consultationId);


	/**
	 * 根据ConsultationId获取对象
	 */
	 T selectByConsultationId(@Param("consultationId") String consultationId);


}
