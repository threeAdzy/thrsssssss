package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRecordVO {
    private Long recordId;
    private Long carId;
    private Long coachId;
    private LocalDateTime updateTime;
    private String name;
}
