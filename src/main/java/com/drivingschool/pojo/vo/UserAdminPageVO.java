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
public class UserAdminPageVO implements Serializable {

    //用户表
    private Long userId;
    private String userNumber;
    private String name;

//    private String userPassword;
    private String identityCard;
    private Integer sex;
    private String phone;
    private Integer state;
    private String userImg;
    //科目表
    private Integer subjectOne;
    private Integer subjectTwo;
    private Integer subjectThree;
    private Integer subjectFour;

    //教练表
    private Long coachId;
    private String coachName;
    private String coachImg;
    //todo  管理员学员分页，未完成
    //驾照等级
    private String classify;
}
