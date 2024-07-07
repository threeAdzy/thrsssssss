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

public class CoachVO implements Serializable {
    private Long coachId;
    private Long carId;
    private String coachNumber;

    private String password;
    private String name;
    private Integer age;
    //教练头像
    private String coachImg;
    private Integer sex;
    private String phone;
    private Integer state;
    //所分配的车的车牌号
    private String carNumber;

    private Integer lv;
    //车辆图片
    private String carImg;
}
