package com.drivingschool.service;

import com.drivingschool.common.result.PageResult;
import com.drivingschool.pojo.dto.*;
import com.drivingschool.pojo.entity.User;
import com.drivingschool.pojo.vo.CoachVO;
import com.drivingschool.pojo.vo.UserInfoVO;

import java.util.List;

public interface UserService {
    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    PageResult pageStudyQuery(UserPageStudyDTO userPageStudyDTO);

    List<User> list();

    User getUserById(Long userId);

    User login(UserLoginDTO userLoginDTO);

    UserInfoVO info(Long currentId);

    void updateSelf(UserUpdateDTO userUpdateDTO);

    PageResult pageAdminUser(UserAdminPageDTO userAdminPageDTO);

    void startOrStop(UserAdminStateDTO userAdminStateDTO);

    void addUser(UserAdminAddDTO userAdminAddDTO);

    void update(UserAdminUpdateDTO userAdminUpdateDTO);

    void deleteById(Long userId);

    List<User> selectUserOfCoach(Long coachId);


    CoachVO myCoach(Long currentId);

    void score(UserScoreDTO userScoreDTO);

    void selectCoach(UserScoreDTO userScoreDTO);

    User autoLogin(UserLoginDTO userLoginDTO);
}
