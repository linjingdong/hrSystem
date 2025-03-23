package com.lin.hr.common.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/23 14:51
 **/
@Data
public class TokenUserInfoDto implements Serializable {

    private static final long serialVersionUID = -3244262035649152692L;

    private String token;
    private String userId;
    private String userName;
    private Boolean admin;
}
