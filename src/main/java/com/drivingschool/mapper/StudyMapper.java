package com.drivingschool.mapper;

import com.drivingschool.pojo.vo.StudyAddDate;
import com.drivingschool.pojo.vo.UserSelfStudyAddDate;
import com.drivingschool.pojo.vo.UserSelfStudyPageVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudyMapper {
    @Insert("INSERT INTO study (user_id, coach_id, start_time, end_time, subject) " +
            "VALUES (#{userId}, #{coachId}, #{begin}, #{end}, #{subject})")
    void insert(StudyAddDate studyAddDate);

    @Delete("delete from study where study_id = #{studyId}")
    void deleteById(Long studyId);

    Page<UserSelfStudyPageVO> pageUserSelf(UserSelfStudyAddDate userSelfStudyAddDate);

    @Delete("DELETE FROM study WHERE user_id = #{userId}")
    void deleteByUserId(Long userId);

    @Delete("DELETE FROM study WHERE coach_id = #{coachId}")
    void deleteByCoachId(Long coachId);
}
