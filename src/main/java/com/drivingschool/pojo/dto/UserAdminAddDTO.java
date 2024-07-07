package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAdminAddDTO implements Serializable {
    private String userNumber;
    private String name;
    private String password;
    private Integer sex ;
    private String identityCard;
    private Long coachId;
    private Long userId;
    private String userImg;
    private String phone;
    private String classify;
}
