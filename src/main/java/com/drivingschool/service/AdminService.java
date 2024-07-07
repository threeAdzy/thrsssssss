package com.drivingschool.service;

import com.drivingschool.pojo.dto.AdminLoginDTO;
import com.drivingschool.pojo.dto.AdminUpdateDTO;
import com.drivingschool.pojo.entity.Admin;

public interface AdminService {
    Admin login(AdminLoginDTO adminLoginDTO);

    Admin info(Long currentId);

    void updateAdmin(AdminUpdateDTO adminUpdateDTO);
}
