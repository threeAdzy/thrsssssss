package com.drivingschool.pojo.dto;

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
public class  UserAddDate implements Serializable {
    private int page;
    private int pageSize;
    private Long coachId;
    private Long studyId;
    private Long userId;
    private String name;
    private Integer sex;
    private Date begin;
    private Date end;
    private Integer subject;
    private String classify;
}
