package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarPageQueryDTO implements Serializable {
    private int page;

    private int pageSize;
    private String carNumber;
    private String name;
    private Integer carState;
}
