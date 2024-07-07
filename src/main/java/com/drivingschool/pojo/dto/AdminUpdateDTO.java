package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class AdminUpdateDTO implements Serializable {
    private String adminNumber;
    private String name;
    private long adminId;
    private Integer lv;
}
