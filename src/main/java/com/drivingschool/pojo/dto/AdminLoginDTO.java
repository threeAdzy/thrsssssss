package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminLoginDTO implements Serializable {
    private String adminNumber;
    private String password;
    private Integer lv;
}
