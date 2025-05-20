package com.lin.hr.manage.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lin.hr.manage.entity.po.RehabilitationTemplateItem;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/19 01:02
 **/
@Data
public class RehabilitationTemplateDto {
    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 康复类型
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

    private List<RehabilitationTemplateItem> rehabilitationTemplateItemList;
}
