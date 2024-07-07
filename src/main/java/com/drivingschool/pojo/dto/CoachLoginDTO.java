package com.drivingschool.pojo.dto;

import lombok.Data;

@Data
public class CoachLoginDTO {
    private String coachNumber;
    private String password;
    private Integer lv;
}
