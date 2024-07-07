package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPageQueryVO implements Serializable {

    private Long coachId;
    private Long userId;
    private String userNumber;
//    private String coachNumber;
    private String name;
    private Integer sex;
    private String phone;
    private String userImg;
    private Integer subjectOne;
    private Integer subjectTwo;
    private Integer subjectThree;
    private Integer subjectFour;
    private String  classify;
}
