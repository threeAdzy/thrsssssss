package com.drivingschool.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class UserAdminUpdateDTO implements Serializable {
    private Long userId;
    private Long coachId;
    private String name;
    private String password;
    private Integer sex;
    private String identityCard;
    private String phone;
    private String userImg;
    private Integer subjectOne;
    private Integer subjectTwo;
    private Integer subjectThree;
    private Integer subjectFour;
}
