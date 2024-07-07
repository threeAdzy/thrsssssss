package com.drivingschool.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAdminStateDTO implements Serializable {

    private Integer state;
    private Long userId;
}
