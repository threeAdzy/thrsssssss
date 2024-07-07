package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CoachDTO implements Serializable {

    private Long coachId;
    private String coachNumber;
    private String password;
    private String name;
    private Integer sex;
    private Integer age;
    private Integer state;
    private String phone;
    private Long carId;
    private String carNumber;
    private String coachImg;

}
