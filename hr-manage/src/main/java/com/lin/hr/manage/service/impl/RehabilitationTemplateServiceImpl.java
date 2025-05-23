package com.lin.hr.manage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.SnowflakeIdGenerator;
import com.lin.hr.manage.entity.dto.RehabilitationTemplateDto;
import com.lin.hr.manage.entity.po.RehabilitationTemplateItem;
import com.lin.hr.manage.entity.po.RehabilitationTherapist;
import com.lin.hr.manage.entity.query.RehabilitationTemplateItemQuery;
import com.lin.hr.manage.entity.query.RehabilitationTherapistQuery;
import com.lin.hr.manage.entity.query.SimplePage;
import com.lin.hr.manage.entity.vo.RehabilitationTemplateVo;
import com.lin.hr.manage.mappers.RehabilitationTemplateItemMapper;
import com.lin.hr.manage.service.RehabilitationTemplateItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lin.hr.manage.entity.query.RehabilitationTemplateQuery;
import com.lin.hr.manage.entity.po.RehabilitationTemplate;
import com.lin.hr.manage.mappers.RehabilitationTemplateMapper;
import com.lin.hr.manage.service.RehabilitationTemplateService;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;
import org.springframework.transaction.annotation.Transactional;


/**
 * 康复训练模板表：后台预设标准训练方案 业务接口实现
 */
@Service("rehabilitationTemplateService")
public class RehabilitationTemplateServiceImpl implements RehabilitationTemplateService {

    @Resource
    private RehabilitationTemplateMapper<RehabilitationTemplate, RehabilitationTemplateQuery> rehabilitationTemplateMapper;
    @Resource
    private RehabilitationTemplateItemMapper<RehabilitationTemplateItem, RehabilitationTemplateItemQuery> templateItemMapper;
    @Resource
    private RehabilitationTemplateItemService templateItemService;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<RehabilitationTemplate> findListByParam(RehabilitationTemplateQuery param) {
        return this.rehabilitationTemplateMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(RehabilitationTemplateQuery param) {
        return this.rehabilitationTemplateMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<RehabilitationTemplate> findListByPage(RehabilitationTemplateQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<RehabilitationTemplate> list = this.findListByParam(param);
        PaginationResultVO<RehabilitationTemplate> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(RehabilitationTemplate bean) {
        return this.rehabilitationTemplateMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<RehabilitationTemplate> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationTemplateMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<RehabilitationTemplate> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationTemplateMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(RehabilitationTemplate bean, RehabilitationTemplateQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationTemplateMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(RehabilitationTemplateQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationTemplateMapper.deleteByParam(param);
    }

    /**
     * 根据TemplateId获取对象
     */
    @Override
    public RehabilitationTemplate getRehabilitationTemplateByTemplateId(String templateId) {
        return this.rehabilitationTemplateMapper.selectByTemplateId(templateId);
    }

    /**
     * 根据TemplateId修改
     */
    @Override
    public Integer updateRehabilitationTemplateByTemplateId(RehabilitationTemplate bean, String templateId) {
        return this.rehabilitationTemplateMapper.updateByTemplateId(bean, templateId);
    }

    /**
     * 根据TemplateId删除
     */
    @Override
    public Integer deleteRehabilitationTemplateByTemplateId(String templateId) {
        return this.rehabilitationTemplateMapper.deleteByTemplateId(templateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createTemplate(RehabilitationTemplateDto dto) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
        RehabilitationTemplate rehabilitationTemplate = new RehabilitationTemplate();
        String templateId = String.valueOf(snowflakeIdGenerator.nextId());
        rehabilitationTemplate.setTemplateId(templateId);
        rehabilitationTemplate.setTemplateName(dto.getTemplateName());
        rehabilitationTemplate.setTypeId(dto.getTypeId());
        rehabilitationTemplate.setTotalDays(dto.getTotalDays());
        rehabilitationTemplate.setSuitableFor(dto.getSuitableFor());
        rehabilitationTemplate.setCreateTime(new Date());
        rehabilitationTemplateMapper.insert(rehabilitationTemplate);
        List<RehabilitationTemplateItem> itemList = dto.getRehabilitationTemplateItemList();
        for (RehabilitationTemplateItem item : itemList) {
            item.setTemplateId(templateId);
            item.setId(String.valueOf(snowflakeIdGenerator.nextId()));
            item.setCreateTime(new Date());
            BeanUtils.copyProperties(item, rehabilitationTemplate);
            templateItemMapper.insert(item);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String templateId) {
        RehabilitationTemplate template = rehabilitationTemplateMapper.selectByTemplateId(templateId);
        if (null == template) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        RehabilitationTemplateItemQuery templateItemQuery = new RehabilitationTemplateItemQuery();
        templateItemQuery.setTemplateId(templateId);
        List<RehabilitationTemplateItem> itemList = templateItemMapper.selectList(templateItemQuery);
        if (null != itemList || !itemList.isEmpty()) {
            for (RehabilitationTemplateItem templateItem : itemList) {
                Integer count = templateItemMapper.deleteById(templateItem.getId());
                if (count == 0) {
                    throw new BusinessException(ResponseCodeEnum.CODE_500);
                }
            }
        }
        rehabilitationTemplateMapper.deleteByTemplateId(templateId);
    }

    @Override
    public RehabilitationTemplateVo getTemplateById(String templateId) {
        RehabilitationTemplate template = rehabilitationTemplateMapper.selectByTemplateId(templateId);
        if (null == template) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        RehabilitationTemplateVo vo = new RehabilitationTemplateVo();
        BeanUtils.copyProperties(template, vo);
        RehabilitationTemplateItemQuery templateItemQuery = new RehabilitationTemplateItemQuery();
        templateItemQuery.setTemplateId(templateId);
        List<RehabilitationTemplateItem> itemList = templateItemMapper.selectList(templateItemQuery);
        vo.setRehabilitationTemplateItems(itemList);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(String templateId, RehabilitationTemplateDto dto) {
        RehabilitationTemplate dbTemplate = rehabilitationTemplateMapper.selectByTemplateId(templateId);
        if (null == dbTemplate) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        
        // 创建更新对象
        RehabilitationTemplate updateTemplate = new RehabilitationTemplate();
        updateTemplate.setTemplateId(templateId); // 设置ID
        
        // 设置要更新的字段
        if (dto.getTemplateName() != null && !dto.getTemplateName().isEmpty()) {
            updateTemplate.setTemplateName(dto.getTemplateName());
        }
        if (dto.getTypeId() != null) {
            updateTemplate.setTypeId(dto.getTypeId());
        }
        if (dto.getSuitableFor() != null) {
            updateTemplate.setSuitableFor(dto.getSuitableFor());
        }
        if (dto.getTotalDays() != null) {
            updateTemplate.setTotalDays(dto.getTotalDays());
        }
        
        // 使用updateByTemplateId而不是updateByParam
        this.rehabilitationTemplateMapper.updateByTemplateId(updateTemplate, templateId);
        
        // 更新训练明细项
        List<RehabilitationTemplateItem> templateItemList = dto.getRehabilitationTemplateItemList();
        if (templateItemList != null && !templateItemList.isEmpty()) {
            for (RehabilitationTemplateItem templateItem : templateItemList) {
                templateItemMapper.insertOrUpdate(templateItem);
            }
        }
    }

    @Override
    public List<RehabilitationTemplateVo> getTemplateByTypeId(String typeId) {
        RehabilitationTemplateQuery templateQuery = new RehabilitationTemplateQuery();
        templateQuery.setTypeId(typeId);
        List<RehabilitationTemplate> rehabilitationTemplates = rehabilitationTemplateMapper.selectList(templateQuery);
        return rehabilitationTemplates.stream().map(template -> {
            return this.getTemplateById(template.getTemplateId());
        }).collect(Collectors.toList());
    }
}