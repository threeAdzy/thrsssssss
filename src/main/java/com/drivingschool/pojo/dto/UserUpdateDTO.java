package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {
    private Long userId;
    private String name;//用户姓名
    private String userNumber;
    private String identityCard;//身份证号
    private Integer sex;//用户性别
    //    用户联系方式
    private String phone;
    private String password;
    private String userImg;
}
