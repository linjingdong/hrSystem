package com.lin.hr.manage.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.manage.entity.po.RehabilitationTemplateItem;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/20 11:05
 **/
@Data
public class RehabilitationTemplateVo {
    /**
     * 模板ID
     */
    private String templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 康复类型ID
     */
    private String typeId;

    /**
     * 总训练天数
     */
    private Integer totalDays;

    /**
     * 适用人群说明
     */
    private String suitableFor;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 子项明细
     */
    List<RehabilitationTemplateItem> rehabilitationTemplateItems;
}
