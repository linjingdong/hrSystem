package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.manage.entity.query.SimplePage;
import org.springframework.stereotype.Service;

import com.lin.hr.manage.entity.query.AiConsultationRecordQuery;
import com.lin.hr.manage.entity.po.AiConsultationRecord;
import com.lin.hr.manage.mappers.AiConsultationRecordMapper;
import com.lin.hr.manage.service.AiConsultationRecordService;



/**
 *  业务接口实现
 */
@Service("aiConsultationRecordService")
public class AiConsultationRecordServiceImpl implements AiConsultationRecordService {

	@Resource
	private AiConsultationRecordMapper<AiConsultationRecord, AiConsultationRecordQuery> aiConsultationRecordMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<AiConsultationRecord> findListByParam(AiConsultationRecordQuery param) {
		return this.aiConsultationRecordMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(AiConsultationRecordQuery param) {
		return this.aiConsultationRecordMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<AiConsultationRecord> findListByPage(AiConsultationRecordQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<AiConsultationRecord> list = this.findListByParam(param);
		PaginationResultVO<AiConsultationRecord> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(AiConsultationRecord bean) {
		return this.aiConsultationRecordMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<AiConsultationRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.aiConsultationRecordMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<AiConsultationRecord> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.aiConsultationRecordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(AiConsultationRecord bean, AiConsultationRecordQuery param) {
		StringTools.checkParam(param);
		return this.aiConsultationRecordMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(AiConsultationRecordQuery param) {
		StringTools.checkParam(param);
		return this.aiConsultationRecordMapper.deleteByParam(param);
	}

	/**
	 * 根据ConsultationId获取对象
	 */
	@Override
	public AiConsultationRecord getAiConsultationRecordByConsultationId(String consultationId) {
		return this.aiConsultationRecordMapper.selectByConsultationId(consultationId);
	}

	/**
	 * 根据ConsultationId修改
	 */
	@Override
	public Integer updateAiConsultationRecordByConsultationId(AiConsultationRecord bean, String consultationId) {
		return this.aiConsultationRecordMapper.updateByConsultationId(bean, consultationId);
	}

	/**
	 * 根据ConsultationId删除
	 */
	@Override
	public Integer deleteAiConsultationRecordByConsultationId(String consultationId) {
		return this.aiConsultationRecordMapper.deleteByConsultationId(consultationId);
	}
}