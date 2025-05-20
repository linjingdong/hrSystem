package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 康复师-患者绑定表：管理多对多指导关系 数据库操作接口
 */
public interface RehabilitationAssignmentMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据AssignmentId更新
	 */
	 Integer updateByAssignmentId(@Param("bean") T t,@Param("assignmentId") String assignmentId);


	/**
	 * 根据AssignmentId删除
	 */
	 Integer deleteByAssignmentId(@Param("assignmentId") String assignmentId);


	/**
	 * 根据AssignmentId获取对象
	 */
	 T selectByAssignmentId(@Param("assignmentId") String assignmentId);


	/**
	 * 根据TherapistIdAndUserId更新
	 */
	 Integer updateByTherapistIdAndUserId(@Param("bean") T t,@Param("therapistId") String therapistId,@Param("userId") String userId);


	/**
	 * 根据TherapistIdAndUserId删除
	 */
	 Integer deleteByTherapistIdAndUserId(@Param("therapistId") String therapistId,@Param("userId") String userId);


	/**
	 * 根据TherapistIdAndUserId获取对象
	 */
	 T selectByTherapistIdAndUserId(@Param("therapistId") String therapistId,@Param("userId") String userId);


}
