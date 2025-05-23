package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;

import com.lin.hr.manage.entity.po.RehabilitationTherapist;
import com.lin.hr.manage.entity.query.RehabilitationTherapistQuery;
import com.lin.hr.manage.service.RehabilitationTherapistService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复师信息表：存储康复师专业资料 Controller
 */
@RestController("rehabilitationTherapistController")
@RequestMapping("/rehabilitationTherapist")
public class RehabilitationTherapistController extends ABaseController {

    @Resource
    private RehabilitationTherapistService rehabilitationTherapistService;

    /**
     * 新增
     */
    @RequestMapping("/add")
    public ResponseVO add(RehabilitationTherapist bean) {
        rehabilitationTherapistService.add(bean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增
     */
    @RequestMapping("/addBatch")
    public ResponseVO addBatch(@RequestBody List<RehabilitationTherapist> listBean) {
        rehabilitationTherapistService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增/修改
     */
    @RequestMapping("/addOrUpdateBatch")
    public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationTherapist> listBean) {
        rehabilitationTherapistService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据TherapistId删除
     */
    @RequestMapping("/deleteRehabilitationTherapistByTherapistId")
    public ResponseVO deleteRehabilitationTherapistByTherapistId(String therapistId) {
        rehabilitationTherapistService.deleteRehabilitationTherapistByTherapistId(therapistId);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据TherapistId查询对象
     */
    @GlobalInterceptor
    @RequestMapping("/getTherapistInfo")
    public ResponseVO<Object> getTherapistInfo(String therapistId) {
        return getSuccessResponseVO(rehabilitationTherapistService.getRehabilitationTherapistByTherapistId(therapistId));
    }

    /**
     * 根据TherapistId修改对象
     */
    @GlobalInterceptor
    @RequestMapping("/updateTherapist")
    public ResponseVO<Object> updateTherapist(RehabilitationTherapist bean, String therapistId) {
        rehabilitationTherapistService.updateRehabilitationTherapistByTherapistId(bean, therapistId);
        return getSuccessResponseVO(null);
    }

    @GlobalInterceptor
    @PostMapping("/addOrUpdateTherapist")
    public ResponseVO<Object> addOrUpdateTherapist(RehabilitationTherapist bean) {
        rehabilitationTherapistService.addOrUpdateTherapist(bean);
        return getSuccessResponseVO("编辑成功");
    }

    /**
     * 根据条件分页查询
     */
    @GlobalInterceptor
    @PostMapping("/loadDataList")
    public ResponseVO<Object> loadDataList(RehabilitationTherapistQuery query) {
        return getSuccessResponseVO(rehabilitationTherapistService.findListByPage(query));
    }
}