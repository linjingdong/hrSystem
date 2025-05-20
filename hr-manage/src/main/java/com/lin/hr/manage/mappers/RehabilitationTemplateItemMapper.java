package com.lin.hr.manage.mappers;

import org.apache.ibatis.annotations.Param;

/**
 * 康复模板明细项 数据库操作接口
 */
public interface RehabilitationTemplateItemMapper<T,P> extends ManageBaseMapper<T,P> {

	/**
	 * 根据Id更新
	 */
	 Integer updateById(@Param("bean") T t,@Param("id") String id);


	/**
	 * 根据Id删除
	 */
	 Integer deleteById(@Param("id") String id);


	/**
	 * 根据Id获取对象
	 */
	 T selectById(@Param("id") String id);


}
