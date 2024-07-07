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
public class Apply implements Serializable {
    private Long applyId;//申请id
    private Long personId;//申请人id
    private Integer lv;//申请人权限
    private Long adminId;//处理的管理员id
    private Long contentId;//申请的内容id
    private Integer applyState;//申请的状态
    private LocalDateTime createTime;//申请创建时间
    private LocalDateTime updateTime;//申请受理时间
    private String remark;//申请备注
}
