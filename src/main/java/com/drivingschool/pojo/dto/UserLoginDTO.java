package com.drivingschool.pojo.dto;

import lombok.Data;

@Data
public class UserLoginDTO {
    private String userNumber;
    private String password;
    private Integer lv;
}
