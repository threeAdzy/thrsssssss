package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ApplyUpdateDTO implements Serializable {
    private Long applyId;
    private Long adminId;
    private Long personId;
    private Long contentId;
    private LocalDateTime updateTime;
    private Integer applyState;
    private Integer lv;

}
