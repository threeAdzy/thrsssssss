package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarAddDTO implements Serializable {
    private String carImg;
    private String carNumber;
    private Long coachId;
}
