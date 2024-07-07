package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginVO implements Serializable {

    private Long adminId;
    private String adminNumber;//账号
    private String name;
    private String token;//jwt令牌
    private Integer lv;
}
