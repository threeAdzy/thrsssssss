package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserScoreDTO implements Serializable {
    private Long coachId;
    private Long userId;
    private Integer score;
}
