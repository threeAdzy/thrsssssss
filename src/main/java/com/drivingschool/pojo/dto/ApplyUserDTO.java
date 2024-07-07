package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyUserDTO implements Serializable {
    private Long contentId;
    private Long personId;
    private Integer lv;
}
