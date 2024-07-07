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
public class Coach implements Serializable {

    private Long coachId;
    private String coachNumber;
    private String name;
    private String password;
    private Integer age;
    private Integer sex;
    private String phone;
    private Integer lv;
    private Integer state;
    private Long carId;
    private String coachImg;
}
