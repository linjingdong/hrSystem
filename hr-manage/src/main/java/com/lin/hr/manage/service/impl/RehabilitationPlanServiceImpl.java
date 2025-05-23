package com.lin.hr.manage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.SnowflakeIdGenerator;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.enums.MessageStatusEnum;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.ChatMessage;
import com.lin.hr.im.entity.po.ChatSession;
import com.lin.hr.im.entity.po.ChatSessionUser;
import com.lin.hr.im.entity.query.ChatMessageQuery;
import com.lin.hr.im.entity.query.ChatSessionQuery;
import com.lin.hr.im.entity.query.ChatSessionUserQuery;
import com.lin.hr.im.mappers.ChatMessageMapper;
import com.lin.hr.im.mappers.ChatSessionMapper;
import com.lin.hr.im.mappers.ChatSessionUserMapper;
import com.lin.hr.im.service.ChatMessageService;
import com.lin.hr.im.websocket.utils.MessageHandler;
import com.lin.hr.manage.entity.dto.RehabilitationPlanDto;
import com.lin.hr.manage.entity.po.RehabilitationPlanItem;
import com.lin.hr.manage.entity.po.RehabilitationTaskRecord;
import com.lin.hr.manage.entity.query.RehabilitationPlanItemQuery;
import com.lin.hr.manage.entity.query.RehabilitationTaskRecordQuery;
import com.lin.hr.manage.entity.vo.RehabilitationPlanVO;
import com.lin.hr.manage.mappers.RehabilitationPlanItemMapper;
import com.lin.hr.manage.mappers.RehabilitationTaskRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import com.lin.hr.manage.entity.query.RehabilitationPlanQuery;
import com.lin.hr.manage.entity.po.RehabilitationPlan;

import com.lin.hr.manage.entity.query.SimplePage;
import com.lin.hr.manage.mappers.RehabilitationPlanMapper;
import com.lin.hr.manage.service.RehabilitationPlanService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 个性化康复计划表：康复师为患者制定的方案 业务接口实现
 */
@Service("rehabilitationPlanService")
public class RehabilitationPlanServiceImpl implements RehabilitationPlanService {

    @Resource
    private RehabilitationPlanMapper<RehabilitationPlan, RehabilitationPlanQuery> rehabilitationPlanMapper;
    @Resource
    private RehabilitationTaskRecordMapper<RehabilitationTaskRecord, RehabilitationTaskRecordQuery> taskRecordMapper;
    @Resource
    private RehabilitationPlanItemMapper<RehabilitationPlanItem, RehabilitationPlanItemQuery> planItemMapper;
    @Resource
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> sessionUserMapper;
    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Resource
    private ChatMessageService chatMessageService;
    @Resource
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Resource
    private MessageHandler messageHandler;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<RehabilitationPlan> findListByParam(RehabilitationPlanQuery param) {
        return this.rehabilitationPlanMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(RehabilitationPlanQuery param) {
        return this.rehabilitationPlanMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<RehabilitationPlanVO> findListByPage(RehabilitationPlanQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        // 分页查询列表
        List<RehabilitationPlan> list = rehabilitationPlanMapper.selectList(param);
        // 转换为VO对象并计算进度
        List<RehabilitationPlanVO> voList = list.stream().map(plan -> {
            RehabilitationPlanVO vo = new RehabilitationPlanVO();
            BeanUtils.copyProperties(plan, vo);
            JSONObject jsonObject = null;
            if (null != plan.getTemplateSnapshotJson()) {
                jsonObject = JSON.parseObject(plan.getTemplateSnapshotJson());
                if (jsonObject.isEmpty()) {
                    vo.setPlanName("默认康复训练计划");
                } else {
                    vo.setPlanName(jsonObject.get("templateName").toString());
                }
            } else {
                vo.setPlanName("默认康复训练计划");
            }
            // 计算计划总天数
            // 将Date转换为LocalDate
            // 毫秒转天数（向上取整）
            long diffInMillies = Math.abs(plan.getEndDate().getTime() - plan.getStartDate().getTime());
            int totalDays = (int) (diffInMillies / (1000 * 60 * 60 * 24)) + 1; // +1包含首尾
            vo.setTotalDay(totalDays);

            // 计算剩余天数
            Date now = new Date();
            if (now.before(plan.getStartDate())) {
                // 未开始
                vo.setRemainingDays(totalDays);
            } else if (now.after(plan.getEndDate())) {
                // 已结束
                vo.setRemainingDays(0);
            } else {
                long abs = Math.abs(plan.getEndDate().getTime() - now.getTime());
                int remainingDay = (int) (abs / (1000 * 60 * 60 * 24)) + 1;
                // 进行中
                vo.setRemainingDays(remainingDay);
            }

            // 查询该计划的所有打卡记录
            RehabilitationTaskRecordQuery taskRecordQuery = new RehabilitationTaskRecordQuery();
            taskRecordQuery.setPlanId(plan.getPlanId());
            List<RehabilitationTaskRecord> records = taskRecordMapper.selectList(taskRecordQuery);

            // 计算完成百分比
            if (records != null && !records.isEmpty()) {
                // 已完成的打卡记录数
                long completedCount = records.stream()
                        .filter(record -> record.getCompleted() != null && record.getCompleted() == 1)
                        .count();

                // 计算百分比（四舍五入到整数）
                int progressRate = Math.round((float) completedCount / totalDays * 100);
                vo.setProgressRate(progressRate);
            } else {
                vo.setProgressRate(0);
            }

            return vo;
        }).collect(Collectors.toList());
        PaginationResultVO<RehabilitationPlanVO> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), voList);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(RehabilitationPlan bean) {
        return this.rehabilitationPlanMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<RehabilitationPlan> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationPlanMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<RehabilitationPlan> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.rehabilitationPlanMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(RehabilitationPlan bean, RehabilitationPlanQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationPlanMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(RehabilitationPlanQuery param) {
        StringTools.checkParam(param);
        return this.rehabilitationPlanMapper.deleteByParam(param);
    }

    /**
     * 根据PlanId获取对象
     */
    @Override
    public RehabilitationPlan getRehabilitationPlanByPlanId(String planId) {
        return this.rehabilitationPlanMapper.selectByPlanId(planId);
    }

    /**
     * 根据PlanId修改
     */
    @Override
    public Integer updateRehabilitationPlanByPlanId(RehabilitationPlan bean, String planId) {
        return this.rehabilitationPlanMapper.updateByPlanId(bean, planId);
    }

    /**
     * 根据PlanId删除
     */
    @Override
    public Integer deleteRehabilitationPlanByPlanId(String planId) {
        return this.rehabilitationPlanMapper.deleteByPlanId(planId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPlan(RehabilitationPlanDto dto, TokenUserInfoDto tokenUserInfoDto) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
        RehabilitationPlan plan = new RehabilitationPlan();
        long planId = snowflakeIdGenerator.nextId();
        BeanUtils.copyProperties(dto, plan);
        plan.setPlanId(String.valueOf(planId));
        rehabilitationPlanMapper.insert(plan);
        List<RehabilitationPlanItem> itemList = dto.getItemList();
        for (RehabilitationPlanItem item : itemList) {
            item.setPlanId(plan.getPlanId());
            item.setDetailId(String.valueOf(snowflakeIdGenerator.nextId()));
            planItemMapper.insert(item);
        }
        String sessionId = StringTools.getChatSession4User(new String[]{dto.getDoctorId(), dto.getUserId()});

        ChatMessage message = new ChatMessage();
        message.setSendUserId(dto.getDoctorId());
        message.setMessageType(MessageTypeEnum.CREATE_PLAN.getType());
        message.setMessageContent(String.format(MessageTypeEnum.CREATE_PLAN.getInitMessage(), dto.getDoctorname()));
        message.setStatus(MessageStatusEnum.SENDED.getStatus());
        message.setContactId(dto.getUserId());
        message.setContactType(UserContactTypeEnum.USER.getType());
        message.setSessionId(sessionId);
        message.setSendTime(new Date().getTime());
        message.setSendUserNickName(dto.getDoctorname());
        chatMessageMapper.insert(message);

        ChatSession chatSession = new ChatSession();
        chatSession.setLastMessage(message.getMessageContent());
        chatSession.setLastReceiveTime(new Date().getTime());
        chatSessionMapper.updateBySessionId(chatSession, sessionId);

        MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
        BeanUtils.copyProperties(message, messageSendDto);
        messageSendDto.setLastMessage(message.getMessageContent());
        messageHandler.sendMessage(messageSendDto);
    }

    @Override
    public void updatePlan(RehabilitationPlanDto dto) {
        RehabilitationPlan dbPlan = rehabilitationPlanMapper.selectByPlanId(dto.getPlanId());
        if (null == dbPlan) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        RehabilitationPlan updatePlan = new RehabilitationPlan();
        BeanUtils.copyProperties(dto, updatePlan);
        updatePlan.setTemplateSnapshotJson(null);
        rehabilitationPlanMapper.updateByPlanId(updatePlan, updatePlan.getPlanId());

        List<RehabilitationPlanItem> itemList = dto.getItemList();
        for (RehabilitationPlanItem item : itemList) {
            planItemMapper.insertOrUpdate(item);
        }
    }

    @Override
    public RehabilitationPlanDto getByPlanId(String planId) {
        RehabilitationPlan dbPlan = rehabilitationPlanMapper.selectByPlanId(planId);
        if (null == dbPlan) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        RehabilitationPlanDto dto = new RehabilitationPlanDto();
        BeanUtils.copyProperties(dbPlan, dto);
        RehabilitationPlanItemQuery planItemQuery = new RehabilitationPlanItemQuery();
        planItemQuery.setPlanId(dto.getPlanId());
        List<RehabilitationPlanItem> items = planItemMapper.selectList(planItemQuery);
        dto.setItemList(items);
        return dto;
    }
}