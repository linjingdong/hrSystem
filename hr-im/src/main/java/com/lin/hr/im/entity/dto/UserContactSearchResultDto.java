package com.lin.hr.im.entity.dto;

import lombok.Data;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/7 19:43
 **/
@Data
public class UserContactSearchResultDto {
    private String contactId;
    private String contactType;
    private String nickName;
    private Integer status;
    private String statusName;
    private Integer sex;
    private String areaName;
}
