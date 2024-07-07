package com.drivingschool.service;

import com.drivingschool.common.result.PageResult;
import com.drivingschool.pojo.dto.ApplyCoachDTO;
import com.drivingschool.pojo.dto.ApplyNoDTO;
import com.drivingschool.pojo.dto.ApplyUpdateDTO;
import com.drivingschool.pojo.dto.ApplypageDTO;

public interface ApplyService {
    void coachApply(ApplyCoachDTO applyCoachDTO);

    void userApply(ApplyCoachDTO applyCoachDTO);

    PageResult pageApply(ApplypageDTO applypageDTO);

    Integer applyNo(ApplyNoDTO applyNoDTO);

    void deleteById(Long applyId);

    void update(ApplyUpdateDTO applyUpdateDTO);
}
