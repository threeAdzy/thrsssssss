package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAdminPageDTO implements Serializable {
    private int page;

    private int pageSize;

    private String userName;

    private Integer sex;//用户性别
    private Integer state;
    private String CoachName;
    private String classify;
    private boolean allsubjects;
}
