package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class ApplypageDTO implements Serializable {
    private int page;
    private int pageSize;
    private Integer applyState;
    //todo 按时间查，未完成
    private Integer lv;
    private String personName;
    private long personId;
//    private Long begin;
//    private Long end;
}
