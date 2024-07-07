package com.drivingschool.mapper;

import com.drivingschool.pojo.dto.UserScoreDTO;
import com.drivingschool.pojo.entity.Evaluate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EvaluateMapper {
    @Select(" SELECT * FROM evaluate WHERE user_id = #{userId} AND coach_id = #{coachId}")
    Evaluate selectByUseridCoachid(Long userId, Long coachId);
    @Update("UPDATE evaluate SET score = #{score} WHERE user_id = #{userId} AND coach_id = #{coachId}")
    void updateScore(UserScoreDTO userScoreDTO);
    @Insert("INSERT INTO evaluate (user_id, coach_id, score) VALUES (#{userId}, #{coachId}, #{score})")
    void insertScore(UserScoreDTO userScoreDTO);
}
