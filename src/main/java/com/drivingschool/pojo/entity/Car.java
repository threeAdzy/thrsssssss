package com.drivingschool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    private Long carId;
    private String carNumber;
    private Integer carState;
//    private Long coachId;
    private String carImg;
}
