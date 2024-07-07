package com.drivingschool.service;

import com.drivingschool.common.result.PageResult;
import com.drivingschool.pojo.dto.StudyDTO;
import com.drivingschool.pojo.dto.UserSelfStudyPageDTO;
import org.springframework.stereotype.Service;


public interface StudyService {
    void save(StudyDTO studyDTO);

    void deleteById(Long studyId);

    PageResult pageUserSelfStudy(UserSelfStudyPageDTO userSelfStudyPageDTO);
}
