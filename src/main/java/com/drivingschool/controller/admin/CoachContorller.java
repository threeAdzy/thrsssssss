package com.drivingschool.controller.admin;

import com.drivingschool.common.constant.JwtClaimsConstant;
import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.properties.JwtProperties;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.common.result.Result;
import com.drivingschool.common.utils.JwtUtil;
import com.drivingschool.pojo.dto.CoachDTO;
import com.drivingschool.pojo.dto.CoachLoginDTO;
import com.drivingschool.pojo.dto.CoachPageQueryDTO;
import com.drivingschool.pojo.dto.UserPageQueryDTO;
import com.drivingschool.pojo.entity.Coach;
import com.drivingschool.pojo.vo.CoachLoginVO;
import com.drivingschool.pojo.vo.CoachVO;
import com.drivingschool.service.CoachService;
import com.drivingschool.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class CoachContorller implements Serializable {

    @Autowired
    private CoachService coachService;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 教练登录
     *
     * @param coachLoginDTO
     * @return
     */
    @PostMapping("coach/login")
    private Result<CoachLoginVO> login(@RequestBody CoachLoginDTO coachLoginDTO) {
        log.info("教练登录：{}", coachLoginDTO);

        Coach coach = coachService.login(coachLoginDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.COACH_ID, coach.getCoachId());
        String token = JwtUtil.createJWT(jwtProperties.getCoachSecretKey(),
                jwtProperties.getCoachTtl(), claims);

        CoachLoginVO coachLoginVO = CoachLoginVO.builder()
                .coachId(coach.getCoachId())
                .coachNumber(coach.getCoachNumber())
                .lv(coach.getLv())
                .token(token)
                .build();

        return Result.success(coachLoginVO);
    }

    /**
     * 获取教练列表（分配车辆）
     *
     * @return
     */
    @GetMapping("/admin/coachName")
    public Result<List<Coach>> list() {
        List<Coach> list = coachService.list();
        return Result.success(list);
    }

    /**
     * 获取教练列表（为用户分配教练）
     *
     * @return
     */
    @GetMapping("/admin/coachOfUser")
    public Result<List<Coach>> listOfUser() {
        List<Coach> list = coachService.listOfUser();
        return Result.success(list);
    }

    /**
     * 教练个人信息回显
     *
     * @return
     */
    @GetMapping("/coach/info")
    public Result<CoachVO> info() {
        log.info("教练个人信息回显，当前id{}", BaseContext.getCurrentId());
        CoachVO coachVO = coachService.info(BaseContext.getCurrentId());

        return Result.success(coachVO);
    }


    /**
     * 教练分页
     *
     * @param coachPageQueryDTO
     * @return
     */
    @GetMapping("/admin/coach/page")
    private Result<PageResult> page(CoachPageQueryDTO coachPageQueryDTO) {

        log.info("教练分页查询{}", coachPageQueryDTO);
        PageResult pageResult = coachService.pageQuery(coachPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 教练评分分页查询
     *
     * @param coachPageQueryDTO
     * @return
     */
    @GetMapping("/admin/coach/ScorePage")
    private Result<PageResult> pageScore(CoachPageQueryDTO coachPageQueryDTO) {

        log.info("教练评分分页查询{}", coachPageQueryDTO);
        PageResult pageResult = coachService.pageScore(coachPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 学员选择教练分页查询
     *
     * @param coachPageQueryDTO
     * @return
     */
    @GetMapping("/user/coach/ScorePage")
    private Result<PageResult> UserpageScore(CoachPageQueryDTO coachPageQueryDTO) {

        log.info("学员选择教练分页查询{}", coachPageQueryDTO);
        PageResult pageResult = coachService.pageScore(coachPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 新增教练
     *
     * @param coachDTO
     * @return
     */
    @PostMapping("/admin/addCoach")
    private Result save(@RequestBody CoachDTO coachDTO) {
        log.info("新增教练{}", coachDTO);
        coachService.save(coachDTO);
        return Result.success();
    }

    /**
     * 根据id查询教练
     *
     * @param coachId
     * @return
     */
    @GetMapping("/admin/getIdCoach")
    private Result<CoachVO> getById(Long coachId) {

        CoachVO coachVO = coachService.getById(coachId);

        return Result.success(coachVO);
    }

    /**
     * 编辑更新教练信息
     *
     * @param coachDTO
     * @return
     */
    @PutMapping("/admin/updateCoach")
    private Result update(@RequestBody CoachDTO coachDTO) {
        log.info("编辑教练信息{}", coachDTO);
        coachService.update(coachDTO);

        return Result.success();
    }

    /**
     * 教练自己更改个人信息
     *
     * @param coachDTO
     * @return
     */
    @PutMapping("/coach/updateCoach")
    private Result updateself(@RequestBody CoachDTO coachDTO) {
        log.info("更新教练信息{}", coachDTO);
        coachService.update(coachDTO);

        return Result.success();
    }

    /**
     * 启用禁用教练账号
     *
     * @param coachDTO
     * @return
     */
    @PostMapping("/admin/coachstatus")
    private Result startOrStop(@RequestBody CoachDTO coachDTO) {
        log.info("启用禁用教练账号，{}", coachDTO);
        coachService.startOrStop(coachDTO);
        return Result.success();
    }

    /**
     * 根据id删除教练
     *
     * @param coachId
     * @return
     */
    @DeleteMapping("/admin/delcoach")
    private Result deleteById(Long coachId) {

        log.info("根据id删除教练，{}", coachId);
        coachService.deleteById(coachId);

        return Result.success();
    }


}
