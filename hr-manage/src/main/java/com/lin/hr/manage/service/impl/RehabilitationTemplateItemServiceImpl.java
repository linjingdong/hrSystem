package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.manage.entity.query.SimplePage;
import org.springframework.stereotype.Service;

import com.lin.hr.manage.entity.query.RehabilitationTemplateItemQuery;
import com.lin.hr.manage.entity.po.RehabilitationTemplateItem;
import com.lin.hr.manage.mappers.RehabilitationTemplateItemMapper;
import com.lin.hr.manage.service.RehabilitationTemplateItemService;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;


/**
 * 康复模板明细项 业务接口实现
 */
@Service("rehabilitationTemplateItemService")
public class RehabilitationTemplateItemServiceImpl implements RehabilitationTemplateItemService {

	@Resource
	private RehabilitationTemplateItemMapper<RehabilitationTemplateItem, RehabilitationTemplateItemQuery> rehabilitationTemplateItemMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<RehabilitationTemplateItem> findListByParam(RehabilitationTemplateItemQuery param) {
		return this.rehabilitationTemplateItemMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(RehabilitationTemplateItemQuery param) {
		return this.rehabilitationTemplateItemMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<RehabilitationTemplateItem> findListByPage(RehabilitationTemplateItemQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<RehabilitationTemplateItem> list = this.findListByParam(param);
		PaginationResultVO<RehabilitationTemplateItem> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(RehabilitationTemplateItem bean) {
		return this.rehabilitationTemplateItemMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<RehabilitationTemplateItem> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationTemplateItemMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<RehabilitationTemplateItem> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.rehabilitationTemplateItemMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(RehabilitationTemplateItem bean, RehabilitationTemplateItemQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationTemplateItemMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(RehabilitationTemplateItemQuery param) {
		StringTools.checkParam(param);
		return this.rehabilitationTemplateItemMapper.deleteByParam(param);
	}

	/**
	 * 根据Id获取对象
	 */
	@Override
	public RehabilitationTemplateItem getRehabilitationTemplateItemById(String id) {
		return this.rehabilitationTemplateItemMapper.selectById(id);
	}

	/**
	 * 根据Id修改
	 */
	@Override
	public Integer updateRehabilitationTemplateItemById(RehabilitationTemplateItem bean, String id) {
		return this.rehabilitationTemplateItemMapper.updateById(bean, id);
	}

	/**
	 * 根据Id删除
	 */
	@Override
	public Integer deleteRehabilitationTemplateItemById(String id) {
		return this.rehabilitationTemplateItemMapper.deleteById(id);
	}
}