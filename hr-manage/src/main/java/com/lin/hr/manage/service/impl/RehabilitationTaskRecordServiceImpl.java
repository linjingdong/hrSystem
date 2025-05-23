package com.lin.hr.manage.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.utils.SnowflakeIdGenerator;
import com.lin.hr.manage.entity.dto.RehabilitationPlanDto;
import com.lin.hr.manage.entity.po.RehabilitationPlan;
import com.lin.hr.manage.entity.query.RehabilitationPlanQuery;
import com.lin.hr.manage.entity.query.SimplePage;
import com.lin.hr.manage.mappers.RehabilitationPlanMapper;
import com.lin.hr.manage.service.RehabilitationPlanService;
import org.springframework.stereotype.Service;

import com.lin.hr.manage.entity.query.RehabilitationTaskRecordQuery;
import com.lin.hr.manage.entity.po.RehabilitationTaskRecord;
import com.lin.hr.manage.mappers.RehabilitationTaskRecordMapper;
import com.lin.hr.manage.service.RehabilitationTaskRecordService;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;
import org.springframework.transaction.annotation.Transactional;


/**
 * 每日训练记录表：患者每日打卡与反馈 业务接口实现
 */
@Service("rehabilitationTaskRecordService")
public class RehabilitationTaskRecordServiceImpl implements RehabilitationTaskRecordService {

    @Resource
    private RehabilitationTaskRecordMapper<RehabilitationTaskRecord, RehabilitationTaskRecordQuery> rehabilitationTaskRecordMapper;
    @Resource
    private RehabilitationPlanMapper<RehabilitationPlan, RehabilitationPlanQuery> rehabilitationPlanMapper;
    @Resource
    private RehabilitationPlanService rehabilitationPlanService;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<RehabilitationTaskRecord> findListByParam(RehabilitationTaskRecordQuery param) {
        return this.rehabilitationTaskRecordMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(RehabilitationTaskRecordQuery param) {
        return this.rehabilitationTaskRecordMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<RehabilitationTaskRecord> findListByPage(RehabilitationTaskRecordQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<RehabilitationTaskRecord> list = this.findListByParam(param);
        PaginationResultVO<RehabilitationTaskRecord> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(RehabilitationTaskRecord bean) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
        String recordId = String.valueOf(snowflakeIdGenerator.nextId());
        bean.setRecordId(recordId);
        RehabilitationPlanDto byPlanId = rehabilitationPlanService.getByPlanId(bean.getPlanId());
        RehabilitationTaskRecordQuery taskRecordQuery = new RehabilitationTaskRecordQuery();
        taskRecordQuery.setPlanId(byPlanId.getPlanId());
        Integer taskCount = rehabilitationTaskRecordMapper.selectCount(taskRecordQuery);
        Integer itemSize = byPlanId.getItemList().size();
        Integer status = 0;
        if (taskCount == 0) {
            status = 1;
        }
        if (taskCount == itemSize - 1) {
            status = 2;
        }
        RehabilitationPlan plan = new RehabilitationPlan();
        plan.setStatus(status);
        rehabilitationPlanMapper.updateByPlanId(plan, byPlanId.getPlanId());
        return this.rehabilitationTaskRecordMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<RehabilitationTaskRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationTaskRecordMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<RehabilitationTaskRecord> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationTaskRecordMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(RehabilitationTaskRecord bean, RehabilitationTaskRecordQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationTaskRecordMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(RehabilitationTaskRecordQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationTaskRecordMapper.deleteByParam(param);
    }

    /**
     * 根据RecordId获取对象
     */
    @Override
    public RehabilitationTaskRecord getRehabilitationTaskRecordByRecordId(String recordId) {
        return this.rehabilitationTaskRecordMapper.selectByRecordId(recordId);
    }

    /**
     * 根据RecordId修改
     */
    @Override
    public Integer updateRehabilitationTaskRecordByRecordId(RehabilitationTaskRecord bean, String recordId) {
        return this.rehabilitationTaskRecordMapper.updateByRecordId(bean, recordId);
    }

    /**
     * 根据RecordId删除
     */
    @Override
    public Integer deleteRehabilitationTaskRecordByRecordId(String recordId) {
        return this.rehabilitationTaskRecordMapper.deleteByRecordId(recordId);
    }

    /**
     * 根据PlanIdAndTrainingDate获取对象
     */
    @Override
    public RehabilitationTaskRecord getRehabilitationTaskRecordByPlanIdAndTrainingDate(String planId, Date trainingDate) {
        return this.rehabilitationTaskRecordMapper.selectByPlanIdAndTrainingDate(planId, trainingDate);
    }

    /**
     * 根据PlanIdAndTrainingDate修改
     */
    @Override
    public Integer updateRehabilitationTaskRecordByPlanIdAndTrainingDate(RehabilitationTaskRecord bean, String planId, Date trainingDate) {
        return this.rehabilitationTaskRecordMapper.updateByPlanIdAndTrainingDate(bean, planId, trainingDate);
    }

    /**
     * 根据PlanIdAndTrainingDate删除
     */
    @Override
    public Integer deleteRehabilitationTaskRecordByPlanIdAndTrainingDate(String planId, Date trainingDate) {
        return this.rehabilitationTaskRecordMapper.deleteByPlanIdAndTrainingDate(planId, trainingDate);
    }
}