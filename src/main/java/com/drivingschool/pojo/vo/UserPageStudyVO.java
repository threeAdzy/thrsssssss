package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPageStudyVO implements Serializable {
    private Long coachId;
    private Long userId;
    private Long studyId;
    private String name;
    private Integer sex;
    private String phone;
    private String userImg;
    private Integer subject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String classify;
}
