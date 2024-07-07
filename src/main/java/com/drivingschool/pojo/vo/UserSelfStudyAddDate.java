package com.drivingschool.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelfStudyAddDate implements Serializable {
    private int page;
    private int pageSize;
    private Long userId;
    private String name;
    private Date begin;
    private Date end;
}
