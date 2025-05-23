package com.lin.hr.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;
import org.springframework.stereotype.Service;


import com.lin.hr.manage.entity.query.RehabilitationTypeQuery;
import com.lin.hr.manage.entity.po.RehabilitationType;

import com.lin.hr.manage.entity.query.SimplePage;
import com.lin.hr.manage.mappers.RehabilitationTypeMapper;
import com.lin.hr.manage.service.RehabilitationTypeService;


/**
 * 康复类型表 业务接口实现
 */
@Service("rehabilitationTypeService")
public class RehabilitationTypeServiceImpl implements RehabilitationTypeService {

    @Resource
    private RehabilitationTypeMapper<RehabilitationType, RehabilitationTypeQuery> rehabilitationTypeMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<RehabilitationType> findListByParam(RehabilitationTypeQuery param) {
        return this.rehabilitationTypeMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(RehabilitationTypeQuery param) {
        return this.rehabilitationTypeMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<RehabilitationType> findListByPage(RehabilitationTypeQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<RehabilitationType> list = this.findListByParam(param);
        PaginationResultVO<RehabilitationType> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(RehabilitationType bean) {
        return this.rehabilitationTypeMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<RehabilitationType> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationTypeMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<RehabilitationType> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationTypeMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(RehabilitationType bean, RehabilitationTypeQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationTypeMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(RehabilitationTypeQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationTypeMapper.deleteByParam(param);
    }

    /**
     * 根据TypeId获取对象
     */
    @Override
    public RehabilitationType getRehabilitationTypeByTypeId(String typeId) {
        return this.rehabilitationTypeMapper.selectByTypeId(typeId);
    }

    /**
     * 根据TypeId修改
     */
    @Override
    public Integer updateRehabilitationTypeByTypeId(RehabilitationType bean, String typeId) {
        return this.rehabilitationTypeMapper.updateByTypeId(bean, typeId);
    }

    /**
     * 根据TypeId删除
     */
    @Override
    public Integer deleteRehabilitationTypeByTypeId(String typeId) {
        return this.rehabilitationTypeMapper.deleteByTypeId(typeId);
    }

    /**
     * 根据TypeCode获取对象
     */
    @Override
    public RehabilitationType getRehabilitationTypeByTypeCode(String typeCode) {
        return this.rehabilitationTypeMapper.selectByTypeCode(typeCode);
    }

    /**
     * 根据TypeCode修改
     */
    @Override
    public Integer updateRehabilitationTypeByTypeCode(RehabilitationType bean, String typeCode) {
        return this.rehabilitationTypeMapper.updateByTypeCode(bean, typeCode);
    }

    /**
     * 根据TypeCode删除
     */
    @Override
    public Integer deleteRehabilitationTypeByTypeCode(String typeCode) {
        return this.rehabilitationTypeMapper.deleteByTypeCode(typeCode);
    }
}