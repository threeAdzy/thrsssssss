package com.drivingschool.controller.admin;

import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.common.result.Result;
import com.drivingschool.pojo.dto.StudyDTO;
import com.drivingschool.pojo.dto.UserSelfStudyPageDTO;
import com.drivingschool.service.StudyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class StudyController {
    @Autowired
    private StudyService studyService;

    /**
     * 教练新增练习
     * @param studyDTO
     * @return
     */
    @PostMapping("/coach/newstudy")
    private Result save(@RequestBody StudyDTO studyDTO) {
        log.info("教练新增练习{}", studyDTO);
        studyDTO.setCoachId(BaseContext.getCurrentId());
        studyService.save(studyDTO);
        return Result.success();
    }

    /**
     * 练习时间安排删除
     * @param studyId
     * @return
     */
    @DeleteMapping("/coach/delStudy")
    private Result deleteById(Long studyId){
        log.info("学习安排记录删除，{}",studyId);
        studyService.deleteById(studyId);
        return Result.success();
    }

    /**
     * 用户自查学习时间分页
     * @param userSelfStudyPageDTO
     * @return
     */
    @GetMapping("/user/studypage")
    public Result<PageResult> pageUserSelfStudy(UserSelfStudyPageDTO userSelfStudyPageDTO){
        userSelfStudyPageDTO.setUserId(BaseContext.getCurrentId());
        log.info("用户自查学习时间分页，{}",userSelfStudyPageDTO);

        PageResult pageResult=studyService.pageUserSelfStudy(userSelfStudyPageDTO);

        return Result.success(pageResult);
    }

}
