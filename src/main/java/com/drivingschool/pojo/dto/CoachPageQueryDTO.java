package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoachPageQueryDTO implements Serializable {
    private int page;
    private int pageSize;
    private String name;
    private Integer sex;
    private Integer state;
}
