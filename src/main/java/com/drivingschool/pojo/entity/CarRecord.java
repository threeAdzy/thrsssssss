package com.drivingschool.pojo.entity;

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
public class CarRecord implements Serializable {
    private Long recordId;
    private Long carId;
    private Long coachId;
    private LocalDateTime updateTime;
//    private LocalDateTime endTime;

}


