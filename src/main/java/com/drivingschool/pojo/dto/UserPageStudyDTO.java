package com.drivingschool.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data

public class UserPageStudyDTO implements Serializable {
    private int page;
    private int pageSize;
    private Long coachId;
    private Long studyId;
    private Long userId;
    private String name;
    private Integer sex;
//    private LocalDateTime[] dateTimes;
    private Long begin;
    private Long end;
    private Integer subject;
    private String classify;

}
