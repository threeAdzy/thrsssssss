package com.drivingschool.pojo.vo;

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
public class ApplyPageVO implements Serializable {
    //todo 申请记录分页返回数据，未完成
    private Long applyId;
    private Long personId;
    private String personName;
    private String personImg;
    private String phone;//申请人联系方式
    private Integer lv;
    private Long contentId;
    private String contentName;
    private String contentImg;

    private Long adminId;

    private String adminName;
    private Integer applyState;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
