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
public class CoachScorePageVO implements Serializable {

    private long coachId;
    private String name;
    private String phone;
    private String coachImg;
    private Integer state;
    private Integer sex;
    private Integer age;
    private Integer avgScore;//平均分
    private Integer scoreOne;
    private Integer scoreTwo;
    private Integer scoreThree;
    private Integer scoreFour;
    private Integer scoreFive;

    private Integer scoreNumber;


}
