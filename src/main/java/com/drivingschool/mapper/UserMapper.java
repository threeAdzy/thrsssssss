package com.drivingschool.mapper;

import com.drivingschool.pojo.dto.UserAddDate;
import com.drivingschool.pojo.dto.UserAdminPageDTO;
import com.drivingschool.pojo.dto.UserPageQueryDTO;
import com.drivingschool.pojo.dto.UserPageStudyDTO;
import com.drivingschool.pojo.entity.User;
import com.drivingschool.pojo.vo.UserAdminPageVO;
import com.drivingschool.pojo.vo.UserInfoVO;
import com.drivingschool.pojo.vo.UserPageQueryVO;
import com.drivingschool.pojo.vo.UserPageStudyVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface UserMapper {
    Page<UserPageQueryVO> pageQuery(UserPageQueryDTO userPageQueryDTO);

//    Page<UserPageStudyVO> pageStudyQuery(UserPageStudyDTO userPageStudyDTO);

    Page<UserPageStudyVO> pageAddDate(UserAddDate userAddDate);

    @Select("SELECT u.*, ps.* " +
            "FROM user u " +
            "JOIN pass_subjects ps ON u.user_id = ps.user_id " +
            "WHERE ps.subject_one = 1 AND u.coach_id = #{coachId}")
    List<User> list(Long coachId);

    @Select("SELECT * from user where user_id=#{userId}")
    User getUserById(Long userId);


    @Select("select * from user where user_number=#{userNumber} and lv=#{lv}")
    User getUserNumber(String userNumber, Integer lv);

    @Select("SELECT u.*, c.name AS coachName,c.coach_id, ps.subject_one, ps.subject_two, ps.subject_three, ps.subject_four,g.classify " +
            "FROM user u " +
            "LEFT JOIN coach c ON u.coach_id = c.coach_id " +
            "LEFT JOIN pass_subjects ps ON u.user_id = ps.user_id " +
            "LEFT JOIN grade g ON u.user_id = g.user_id " +
            "WHERE u.user_id = #{userId}")
    UserInfoVO getUserInfoById(Long userId);

    void updateSelf(User user);

    Page<UserAdminPageVO> pageAdminUser(UserAdminPageDTO userAdminPageDTO);

    void update(User user);

    void insert(User user);

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    void deleteById(Long userId);

    @Select("SELECT u.* FROM user u WHERE u.coach_id = #{coachId}")
    List<User> selectUserOfCoach(Long coachId);

    @Update("UPDATE user SET coach_id = NULL WHERE coach_id = #{coachId}")
    void updateCoach(Long coachId);
}
