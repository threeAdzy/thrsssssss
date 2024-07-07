package com.drivingschool.pojo.dto;

import lombok.Data;

@Data
public class StudyDTO {
    private Long userId;
    private Long coachId;
    private Long begin;
    private Long end;
    private Integer subject;
}
