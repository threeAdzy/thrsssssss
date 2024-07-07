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
public class CoachLoginVO implements Serializable{
    private Long coachId;
    private String coachNumber;//账号
    private String name;
    private String token;//jwt令牌
    private Integer lv;
}




