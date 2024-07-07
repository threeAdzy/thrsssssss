package com.drivingschool.controller.admin;


import com.drivingschool.common.constant.JwtClaimsConstant;
import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.properties.JwtProperties;
import com.drivingschool.common.result.Result;
import com.drivingschool.common.utils.JwtUtil;
import com.drivingschool.pojo.dto.AdminLoginDTO;
import com.drivingschool.pojo.dto.AdminUpdateDTO;
import com.drivingschool.pojo.dto.UserUpdateDTO;
import com.drivingschool.pojo.entity.Admin;
import com.drivingschool.pojo.vo.AdminLoginVO;
import com.drivingschool.service.AdminService;
//import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@Tag(name = "管理员相关接口")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     *
     * @param adminLoginDTO
     * @return
     */
    @PostMapping("admin/login")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        log.info("管理员登录；{}", adminLoginDTO);

        Admin admin = adminService.login(adminLoginDTO);
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.Admin_ID, admin.getAdminId());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(), claims);

        AdminLoginVO adminLoginVO = AdminLoginVO.builder().adminId(admin.getAdminId())
                .name(admin.getName()).adminNumber(admin.getAdminNumber())
                .token(token).lv(admin.getLv()).build();

        return Result.success(adminLoginVO);
    }

    /**
     * 管理员个人信息回显
     *
     * @return
     */
    @GetMapping("admin/info")
    public Result<AdminLoginVO> info() {
        log.info("管理员个人信息回显，当前id；{}", BaseContext.getCurrentId());
        Admin admin = adminService.info(BaseContext.getCurrentId());
        AdminLoginVO adminLoginVO = AdminLoginVO.builder().adminId(admin.getAdminId())
                .name(admin.getName()).adminNumber(admin.getAdminNumber())
                .lv(admin.getLv()).build();

        return Result.success(adminLoginVO);
    }

    @PutMapping("/admin/updateAdmin")
    public Result updateAdmin(@RequestBody AdminUpdateDTO adminUpdateDTO) {
        adminUpdateDTO.setAdminId(BaseContext.getCurrentId());
        log.info("管理员更新自己个人信息，{}", adminUpdateDTO);
        adminService.updateAdmin(adminUpdateDTO);
        return Result.success();
    }
}
