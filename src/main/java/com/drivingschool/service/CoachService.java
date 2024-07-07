package com.drivingschool.service;

import com.drivingschool.common.result.PageResult;
import com.drivingschool.pojo.dto.CoachDTO;
import com.drivingschool.pojo.dto.CoachLoginDTO;
import com.drivingschool.pojo.dto.CoachPageQueryDTO;
import com.drivingschool.pojo.entity.Coach;
import com.drivingschool.pojo.vo.CoachVO;

import java.util.List;

public interface CoachService {
    PageResult pageQuery(CoachPageQueryDTO coachPageQueryDTO);

    void save(CoachDTO coachDTO);

    CoachVO getById(Long coachId);

    void update(CoachDTO coachDTO);

    void startOrStop(CoachDTO coachDTO);

    Coach login(CoachLoginDTO coachLoginDTO);

    CoachVO info(Long currentId);

    List<Coach> list();

    List<Coach> listOfUser();

    void deleteById(Long coachId);

    PageResult pageScore(CoachPageQueryDTO coachPageQueryDTO);
}
