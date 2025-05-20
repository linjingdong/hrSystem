package com.lin.hr.manage.controller;

import java.util.List;

import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.manage.entity.query.AiConsultationRecordQuery;
import com.lin.hr.manage.entity.po.AiConsultationRecord;
import com.lin.hr.manage.service.AiConsultationRecordService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  Controller
 */
@RestController("aiConsultationRecordController")
@RequestMapping("/aiConsultationRecord")
public class AiConsultationRecordController extends ABaseController {

	@Resource
	private AiConsultationRecordService aiConsultationRecordService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(AiConsultationRecordQuery query){
		return getSuccessResponseVO(aiConsultationRecordService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(AiConsultationRecord bean) {
		aiConsultationRecordService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<AiConsultationRecord> listBean) {
		aiConsultationRecordService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<AiConsultationRecord> listBean) {
		aiConsultationRecordService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ConsultationId查询对象
	 */
	@RequestMapping("/getAiConsultationRecordByConsultationId")
	public ResponseVO getAiConsultationRecordByConsultationId(String consultationId) {
		return getSuccessResponseVO(aiConsultationRecordService.getAiConsultationRecordByConsultationId(consultationId));
	}

	/**
	 * 根据ConsultationId修改对象
	 */
	@RequestMapping("/updateAiConsultationRecordByConsultationId")
	public ResponseVO updateAiConsultationRecordByConsultationId(AiConsultationRecord bean,String consultationId) {
		aiConsultationRecordService.updateAiConsultationRecordByConsultationId(bean,consultationId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ConsultationId删除
	 */
	@RequestMapping("/deleteAiConsultationRecordByConsultationId")
	public ResponseVO deleteAiConsultationRecordByConsultationId(String consultationId) {
		aiConsultationRecordService.deleteAiConsultationRecordByConsultationId(consultationId);
		return getSuccessResponseVO(null);
	}
}