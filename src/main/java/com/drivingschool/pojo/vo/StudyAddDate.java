package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyAddDate {
    private Long userId;
    private Long coachId;
    private Date begin;
    private Date end;
    private Integer subject;
}
