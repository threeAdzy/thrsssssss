package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelfStudyPageVO implements Serializable {
    private String name;//教练姓名

    private Integer subject;

    private String coachImg;

    private String phone;//教练联系方式
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
