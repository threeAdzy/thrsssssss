package com.drivingschool.controller.admin;

import com.drivingschool.common.constant.JwtClaimsConstant;
import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.properties.JwtProperties;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.common.result.Result;
import com.drivingschool.common.utils.JwtUtil;
import com.drivingschool.pojo.dto.*;
import com.drivingschool.pojo.entity.Coach;
import com.drivingschool.pojo.entity.User;
import com.drivingschool.pojo.vo.CoachLoginVO;
import com.drivingschool.pojo.vo.CoachVO;
import com.drivingschool.pojo.vo.UserInfoVO;
import com.drivingschool.pojo.vo.UserLoginVO;
import com.drivingschool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userAdminAddDTO
     * @return
     */
    @PostMapping("/register")
    private Result registerUser(@RequestBody UserAdminAddDTO userAdminAddDTO) {
        log.info("用户注册，{}", userAdminAddDTO);

        userService.addUser(userAdminAddDTO);
        return Result.success();
    }

    /**
     * 用户注册后自动登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/user/Autologin")
    private Result<UserLoginVO> autoLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户注册完成之后自动登录：{}", userLoginDTO);

        User user = userService.autoLogin(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .userId(user.getUserId())
                .userNumber(user.getUserNumber())
                .lv(user.getLv()).token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 用户登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("user/login")
    private Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getUserId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .userId(user.getUserId())
                .userNumber(user.getUserNumber())
                .lv(user.getLv()).token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 学员自查个人信息回显
     *
     * @return
     */
    @GetMapping("/user/info")
    public Result<UserInfoVO> info() {
        log.info("学员自查个人信息回显，当前学员id{}", BaseContext.getCurrentId());
        UserInfoVO userInfoVO = userService.info(BaseContext.getCurrentId());
        return Result.success(userInfoVO);
    }

    /**
     * 学员查看自己教练信息
     *
     * @return
     */
    @GetMapping("/user/myCaoch")
    public Result<CoachVO> myCoach() {
        log.info("学员查自己教练信息，当前学员id{}", BaseContext.getCurrentId());
        CoachVO coachVO = userService.myCoach(BaseContext.getCurrentId());
        return Result.success(coachVO);
    }

    /**
     * 教练分页查询自己学员
     *
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/coach/user/page")
    public Result<PageResult> pageUser(UserPageQueryDTO userPageQueryDTO) {

        userPageQueryDTO.setCoachId(BaseContext.getCurrentId());
        log.info("教练分页查询自己学员：{}", userPageQueryDTO);
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 练习安排学生分页
     *
     * @param userPageStudyDTO
     * @return
     */
    @GetMapping("/coach/user/studypage")
    public Result<PageResult> pageStudyUser(UserPageStudyDTO userPageStudyDTO) {

        userPageStudyDTO.setCoachId(BaseContext.getCurrentId());
        log.info("练习安排分页查询：{}", userPageStudyDTO);

        PageResult pageResult = userService.pageStudyQuery(userPageStudyDTO);
        return Result.success(pageResult);
    }

    /**
     * 获取学生列表
     *
     * @return
     */
    @GetMapping("/coach/userName")
    public Result<List<User>> list() {
        log.info("获取学生列表。。。。。。。。。。。。。。。。");
        List<User> list = userService.list();
        return Result.success(list);
    }

    /**
     * 根据id查用户
     *
     * @param userId
     * @return
     */
    @GetMapping("/coach/userId")
    public Result<User> getUserById(Long userId) {
        User user = userService.getUserById(userId);
        return Result.success(user);
    }

    /**
     * 学员更新自己个人信息
     *
     * @param userUpdateDTO
     * @return
     */

    @PutMapping("/user/updateUser")
    public Result updateSelf(@RequestBody UserUpdateDTO userUpdateDTO) {
        log.info("学员更新自己个人信息，{}", userUpdateDTO);
        userService.updateSelf(userUpdateDTO);
        return Result.success();
    }

    /**
     * 管理员分页查询学员
     *
     * @param userAdminPageDTO
     * @return
     */
    @GetMapping("/admin/user/page")
    public Result<PageResult> page(UserAdminPageDTO userAdminPageDTO) {
        log.info("管理员分页查询学员{}", userAdminPageDTO);
        PageResult pageResult = userService.pageAdminUser(userAdminPageDTO);

        return Result.success(pageResult);
    }

    /**
     * 管理员更新用户信息
     *
     * @param userAdminUpdateDTO
     * @return
     */
    @PutMapping("/admin/updateUser")
    private Result update(@RequestBody UserAdminUpdateDTO userAdminUpdateDTO) {
        log.info("编辑用户信息{}", userAdminUpdateDTO);
        userService.update(userAdminUpdateDTO);

        return Result.success();
    }

    /**
     * 启用禁用学员账号
     *
     * @param userAdminStateDTO
     * @return
     */
    @PostMapping("/admin/userstatus")
    private Result startOrStop(@RequestBody UserAdminStateDTO userAdminStateDTO) {
        log.info("启用禁用学员账号，{}", userAdminStateDTO);
        userService.startOrStop(userAdminStateDTO);
        return Result.success();
    }

    /**
     * 学员评价教练
     *
     * @param userScoreDTO
     * @return
     */
    @PostMapping("/user/score")
    public Result score(@RequestBody UserScoreDTO userScoreDTO) {
        userScoreDTO.setUserId(BaseContext.getCurrentId());
        log.info("学员评价教练，{}", userScoreDTO);
        userService.score(userScoreDTO);
        return Result.success();
    }

    /**
     * 管理员根据id删除用户
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/admin/deluser")
    private Result deleteById(Long userId) {

        log.info("根据id删除用户，{}", userId);
        userService.deleteById(userId);

        return Result.success();
    }

    /**
     * 管理员添加学员
     *
     * @param userAdminAddDTO
     * @return
     */
    @PostMapping("/admin/addUser")
    private Result addUser(@RequestBody UserAdminAddDTO userAdminAddDTO) {
        log.info("管理员添加学员，{}", userAdminAddDTO);

        userService.addUser(userAdminAddDTO);
        return Result.success();
    }

    /**
     * 根据教练id查询用户列表
     *
     * @param coachId
     * @return
     */
    @GetMapping("/admin/selectUserOfCoach")
    private Result<List<User>> selectUserOfCoach(Long coachId) {
        List<User> list = userService.selectUserOfCoach(coachId);

        return Result.success(list);
    }

    /**
     * 用户选择教练
     *
     * @param userScoreDTO
     * @return
     */
    @PostMapping("/user/selectCoach")
    private Result selectCoach(@RequestBody UserScoreDTO userScoreDTO) {
        userScoreDTO.setUserId(BaseContext.getCurrentId());
        log.info("学员选择教练{}", userScoreDTO);
        userService.selectCoach(userScoreDTO);
        return Result.success();
    }

}
