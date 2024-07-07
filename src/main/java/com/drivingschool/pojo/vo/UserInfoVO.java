package com.drivingschool.pojo.vo;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO implements Serializable {
    private Long userId;
    private Long coachId;
    private String name;//用户姓名
    private String userNumber;
    private String identityCard;//身份证号
    private Integer sex;//用户性别
    //    用户联系方式
    private String phone;
    private Integer lv;
    private Integer state;
    private String userImg;
    private String coachName;
    private Integer subjectOne;
    private Integer subjectTwo;
    private Integer subjectThree;
    private Integer subjectFour;

    private String classify;

}
