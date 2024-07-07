package com.drivingschool.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Study {
    private Long studyId;
    private Long userId;
    private Long coachId;
    private Integer subject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
