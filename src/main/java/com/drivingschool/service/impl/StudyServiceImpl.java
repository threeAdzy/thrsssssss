package com.drivingschool.service.impl;

import com.drivingschool.common.constant.MessageConstant;
import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.exception.PassSubjectException;
import com.drivingschool.common.exception.StudyDateException;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.mapper.PassSubjectMapper;
import com.drivingschool.mapper.StudyMapper;
import com.drivingschool.pojo.dto.StudyDTO;
import com.drivingschool.pojo.dto.UserAddDate;
import com.drivingschool.pojo.dto.UserSelfStudyPageDTO;
import com.drivingschool.pojo.entity.PassSubjects;
import com.drivingschool.pojo.vo.StudyAddDate;
import com.drivingschool.pojo.vo.UserSelfStudyAddDate;
import com.drivingschool.pojo.vo.UserSelfStudyPageVO;
import com.drivingschool.service.StudyService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@Slf4j
public class StudyServiceImpl implements StudyService {
    @Autowired
    private StudyMapper studyMapper;
    @Autowired
    private PassSubjectMapper passSubjectMapper;

    @Override
    public void save(StudyDTO studyDTO) {
//      根据id查询该学员科目通过情况
        PassSubjects passSubjects = passSubjectMapper.passById(studyDTO.getUserId());

//      获取当前时间
        Long nowTime = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //已通过的科目被选中抛异常
        if (studyDTO.getSubject() == 2 && passSubjects.getSubjectTwo() == 1) {
            throw new PassSubjectException(MessageConstant.SUBJECT_PASS);
        }
        if (studyDTO.getSubject() == 3 && passSubjects.getSubjectThree() == 1) {
            throw new PassSubjectException(MessageConstant.SUBJECT_PASS);
        }
        //练习时间异常
        if (studyDTO.getBegin() <= nowTime) {
            throw new StudyDateException(MessageConstant.BEGIN_TIME_NOW);
        }

        if (studyDTO.getBegin() >= studyDTO.getEnd()) {
            throw new StudyDateException(MessageConstant.END_TIME_BEGIN);
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateBegin = null;
        Date dateEnd = null;
        String begin = null, end = null;

        begin = format.format(studyDTO.getBegin());
        end = format.format(studyDTO.getEnd());
        try {
            dateBegin = format.parse(begin);
            dateEnd = format.parse(end);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        StudyAddDate studyAddDate = StudyAddDate.builder()
                .subject(studyDTO.getSubject())
                .coachId(studyDTO.getCoachId())
                .userId(studyDTO.getUserId())
                .begin(dateBegin)
                .end(dateEnd).build();

        studyMapper.insert(studyAddDate);

    }

    /**
     * 根据id删除学习安排
     *
     * @param studyId
     */
    @Override
    public void deleteById(Long studyId) {
        studyMapper.deleteById(studyId);
    }

    /**
     * 用户自查学生分页
     *
     * @param userSelfStudyPageDTO
     * @return
     */
    @Override
    public PageResult pageUserSelfStudy(UserSelfStudyPageDTO userSelfStudyPageDTO) {
        if (userSelfStudyPageDTO.getCoachName() != null && userSelfStudyPageDTO.getCoachName() .length() != 0
        ) {
            log.info("aaaaaaaaaaaaaaaa");
            userSelfStudyPageDTO.setPage(1);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        PageHelper.startPage(userSelfStudyPageDTO.getPage(), userSelfStudyPageDTO.getPageSize());

        Date dateBegin = null;
        Date dateEnd = null;
        String begin = null, end = null;

        UserSelfStudyAddDate userSelfStudyAddDate = UserSelfStudyAddDate.builder()
                .page(userSelfStudyPageDTO.getPage())
                .pageSize(userSelfStudyPageDTO.getPageSize())
                .name(userSelfStudyPageDTO.getCoachName())
                .userId(userSelfStudyPageDTO.getUserId())
                .build();
//log.info("试探userId{}",userSelfStudyPageDTO);
        if (userSelfStudyPageDTO.getBegin() != null) {
            begin = format.format(userSelfStudyPageDTO.getBegin());

            try {
                dateBegin = format.parse(begin);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            userSelfStudyAddDate.setBegin(dateBegin);

        }
        //判断结束时间
        if (userSelfStudyPageDTO.getEnd() != null) {
            Long ea = userSelfStudyPageDTO.getEnd() + 86400000;
            end = format.format(ea);
            try {
                dateEnd = format.parse(end);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            userSelfStudyAddDate.setEnd(dateEnd);
        }

        Page<UserSelfStudyPageVO> page=studyMapper.pageUserSelf(userSelfStudyAddDate);

        return new PageResult(page.getTotal(), page.getResult());
    }
}

























