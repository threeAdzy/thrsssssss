package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserSelfStudyPageDTO implements Serializable {
    private int page;
    private int pageSize;
    private Long userId;
    private String CoachName;
    private Long begin;
    private Long end;
}
