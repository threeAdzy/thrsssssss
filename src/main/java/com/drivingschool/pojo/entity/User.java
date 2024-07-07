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
public class User implements Serializable {

    private Long userId;
    private String userNumber;
    private String name;
    private String password;
    private String identityCard;
    private Integer sex;
    private String phone;
    private Integer lv;
    private Integer state;
    private Long coachId;
    private String userImg;
}