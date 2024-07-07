package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class ApplyNoDTO implements Serializable {
    private Integer lv;
    private Long personId;
}
