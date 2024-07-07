package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class CarRepairsDTO implements Serializable {
    private Long coachId;
    private Long carId;
}
