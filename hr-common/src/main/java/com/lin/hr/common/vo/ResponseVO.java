package com.lin.hr.common.vo;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseVO<T> {
    private String status;
    private Integer code;
    private String info;
    private T data;
}
