package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class CarUpdateDTO implements Serializable {


        private Integer carState;
        private Long carId;
        private String carImg;
        private String carNumber;
        private Long coachId;


}
