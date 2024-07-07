package com.drivingschool.controller.admin;

import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.common.result.Result;
import com.drivingschool.pojo.dto.ApplyCoachDTO;
import com.drivingschool.pojo.dto.ApplyNoDTO;
import com.drivingschool.pojo.dto.ApplyUpdateDTO;
import com.drivingschool.pojo.dto.ApplypageDTO;
import com.drivingschool.service.ApplyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 申请管理
 */
@RestController
@Slf4j
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    /**
     * 教练申请更换车辆
     *
     * @param applyCoachDTO
     * @return
     */
    @PostMapping("/coach/applyCar")
    public Result coachApply(@RequestBody ApplyCoachDTO applyCoachDTO) {
        applyCoachDTO.setPersonId(BaseContext.getCurrentId());
        log.info("教练申请，{}", applyCoachDTO);
        applyService.coachApply(applyCoachDTO);
        return Result.success();
    }

    /**
     * 学员申请更换教练
     *
     * @param applyCoachDTO
     * @return
     */
    @PostMapping("/user/applyCoach")
    public Result userApply(@RequestBody ApplyCoachDTO applyCoachDTO) {
        applyCoachDTO.setPersonId(BaseContext.getCurrentId());
        log.info("学员申请更换教练，{}", applyCoachDTO);
        applyService.userApply(applyCoachDTO);
        return Result.success();
    }

    /**
     * 管理员分页查申请记录
     *
     * @param applypageDTO
     * @return
     */
    @GetMapping("/admin/apply/page")
    public Result<PageResult> pageApply(ApplypageDTO applypageDTO) {
        log.info("管理员分页查询申请记录，{}", applypageDTO);
        PageResult pageResult = applyService.pageApply(applypageDTO);

        return Result.success(pageResult);
    }

    /**
     * 教练获取未处理申请数
     *
     * @param applyNoDTO
     * @return
     */
    @GetMapping("/coach/ApplyNo")
    public Result coachApplyNo(ApplyNoDTO applyNoDTO) {
        applyNoDTO.setPersonId(BaseContext.getCurrentId());
        log.info("教练获取未处理申请数：{}", applyNoDTO);
        Integer applyNo = applyService.applyNo(applyNoDTO);
        return Result.success(applyNo);
    }

    /**
     * 学员获取未处理申请数
     *
     * @param applyNoDTO
     * @return
     */
    @GetMapping("/user/ApplyNo")
    public Result userApplyNo(ApplyNoDTO applyNoDTO) {
        applyNoDTO.setPersonId(BaseContext.getCurrentId());
        log.info("学员获取未处理申请数：{}", applyNoDTO);
        Integer applyNo = applyService.applyNo(applyNoDTO);
        return Result.success(applyNo);
    }

    /**
     * 教练查询自己申请分页
     *
     * @param applypageDTO
     * @return
     */
    @GetMapping("/coach/apply/page")
    public Result<PageResult> pageCoachApply(ApplypageDTO applypageDTO) {

        applypageDTO.setPersonId(BaseContext.getCurrentId());
        log.info("教练分页查询自己申请记录，{}", applypageDTO);
        PageResult pageResult = applyService.pageApply(applypageDTO);

        return Result.success(pageResult);
    }

    /**
     * 学员查询自己申请分页
     *
     * @param applypageDTO
     * @return
     */
    @GetMapping("/user/apply/page")
    public Result<PageResult> pageUserApply(ApplypageDTO applypageDTO) {

        applypageDTO.setPersonId(BaseContext.getCurrentId());
        log.info("学员分页查询自己申请记录，{}", applypageDTO);
        PageResult pageResult = applyService.pageApply(applypageDTO);

        return Result.success(pageResult);
    }

    /**
     * 教练撤销申请
     *
     * @param applyId
     * @return
     */
    @DeleteMapping("/coach/applydelete")
    public Result coachDel(Long applyId) {
        log.info("根据id撤销申请，教练，{}", applyId);
        applyService.deleteById(applyId);
        return Result.success();
    }
    /**
     * 学员撤销申请
     *
     * @param applyId
     * @return
     */
    @DeleteMapping("/user/applydelete")
    public Result userDel(Long applyId) {
        log.info("根据id撤销申请，教练，{}", applyId);
        applyService.deleteById(applyId);
        return Result.success();
    }
    /**
     * 管理员删除记录
     *
     * @param applyId
     * @return
     */
    @DeleteMapping("/admin/applydelete")
    public Result adminDel(Long applyId) {
        log.info("根据id删除申请，{}", applyId);
        applyService.deleteById(applyId);
        return Result.success();
    }

    /**
     * 管理员修改申请状态
     * @param applyUpdateDTO
     * @return
     */
    @PostMapping("/admin/applyUpdate")
    public Result applyUpdate(@RequestBody ApplyUpdateDTO applyUpdateDTO) {
        applyUpdateDTO.setAdminId(BaseContext.getCurrentId());
        applyUpdateDTO.setUpdateTime(LocalDateTime.now());
        log.info("管理员修改申请状态，{}", applyUpdateDTO);
        applyService.update(applyUpdateDTO);
        return Result.success();
    }
}
