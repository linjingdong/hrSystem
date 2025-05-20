package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.dto.RehabilitationTemplateDto;
import com.lin.hr.manage.entity.query.RehabilitationTemplateQuery;
import com.lin.hr.manage.entity.po.RehabilitationTemplate;
import com.lin.hr.manage.entity.vo.RehabilitationTemplateVo;
import com.lin.hr.manage.service.RehabilitationTemplateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 康复训练模板表：后台预设标准训练方案 Controller
 */
@RestController("rehabilitationTemplateController")
@RequestMapping("/rehabilitationTemplate")
public class RehabilitationTemplateController extends ABaseController {

    @Resource
    private RehabilitationTemplateService rehabilitationTemplateService;

    /**
     * 新增
     */
    @RequestMapping("/add")
    public ResponseVO add(RehabilitationTemplate bean) {
        rehabilitationTemplateService.add(bean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增
     */
    @RequestMapping("/addBatch")
    public ResponseVO addBatch(@RequestBody List<RehabilitationTemplate> listBean) {
        rehabilitationTemplateService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 批量新增/修改
     */
    @RequestMapping("/addOrUpdateBatch")
    public ResponseVO addOrUpdateBatch(@RequestBody List<RehabilitationTemplate> listBean) {
        rehabilitationTemplateService.addBatch(listBean);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据TemplateId修改对象
     */
    @RequestMapping("/updateRehabilitationTemplateByTemplateId")
    public ResponseVO updateRehabilitationTemplateByTemplateId(RehabilitationTemplate bean, String templateId) {
        rehabilitationTemplateService.updateRehabilitationTemplateByTemplateId(bean, templateId);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据TemplateId删除
     */
    @RequestMapping("/deleteRehabilitationTemplateByTemplateId")
    public ResponseVO deleteRehabilitationTemplateByTemplateId(String templateId) {
        rehabilitationTemplateService.deleteRehabilitationTemplateByTemplateId(templateId);
        return getSuccessResponseVO(null);
    }

    /**
     * 创建模板
     */
    @GlobalInterceptor
    @PostMapping("/createTemplate")
    public ResponseVO<Object> createTemplate(@RequestBody RehabilitationTemplateDto dto) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        rehabilitationTemplateService.createTemplate(dto);
        return getSuccessResponseVO("创建训练计划模板成功");
    }

    /**
     * 删除模板
     */
    @GlobalInterceptor
    @PostMapping("/deleteTemplate")
    public ResponseVO<Object> deleteTemplate(String templateId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        rehabilitationTemplateService.deleteById(templateId);
        return getSuccessResponseVO("删除训练计划模板成功");
    }

    /**
     * 根据条件分页查询
     */
    @RequestMapping("/loadDataList")
    @GlobalInterceptor
    public ResponseVO<Object> loadDataList(RehabilitationTemplateQuery query) {
        return getSuccessResponseVO(rehabilitationTemplateService.findListByPage(query));
    }

    /**
     * 根据id查询模板
     */
    @GlobalInterceptor
    @RequestMapping("/getTemplateById")
    public ResponseVO<Object> getTemplateById(String templateId) {
        RehabilitationTemplateVo vo = rehabilitationTemplateService.getTemplateById(templateId);
        return getSuccessResponseVO(null);
    }

    /**
     * 根据id修改模板
     */
    @GlobalInterceptor
    @RequestMapping("/updateTemplate")
    public ResponseVO<Object> updateTemplateById(RehabilitationTemplateDto dto, String templateId) {

        rehabilitationTemplateService.updateById(templateId, dto);
        return getSuccessResponseVO(null);
    }
}