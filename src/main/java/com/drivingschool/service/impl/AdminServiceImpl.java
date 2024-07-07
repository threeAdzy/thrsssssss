package com.drivingschool.service.impl;

import com.drivingschool.common.constant.MessageConstant;
import com.drivingschool.common.exception.AccountNotFoundException;
import com.drivingschool.common.exception.PasswordErrorException;
import com.drivingschool.mapper.AdminMapper;
import com.drivingschool.pojo.dto.AdminLoginDTO;
import com.drivingschool.pojo.dto.AdminUpdateDTO;
import com.drivingschool.pojo.entity.Admin;
import com.drivingschool.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员登录
     * @param adminLoginDTO
     * @return
     */
    @Override
    public Admin login(AdminLoginDTO adminLoginDTO) {

        String adminNumber= adminLoginDTO.getAdminNumber();
        String password=adminLoginDTO.getPassword();
        Integer lv=adminLoginDTO.getLv();

        Admin admin=adminMapper.getadminNumber(adminNumber,lv);

        if (admin == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }


        return admin;
    }

    @Override
    public Admin info(Long currentId) {
        Admin admin=adminMapper.getadminId(currentId);
        return admin;
    }

    @Override
    public void updateAdmin(AdminUpdateDTO adminUpdateDTO) {
        Admin admin=new Admin();
        BeanUtils.copyProperties(adminUpdateDTO,admin);
        adminMapper.updateAdmin(admin);
    }


}
