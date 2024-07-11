package com.drivingschool.mapper;

import com.drivingschool.pojo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select *from admin where admin_number=#{adminNumber} and lv=#{lv}")
    Admin getadminNumber(String adminNumber, Integer lv);

    @Select("select *from admin where admin_id=#{adminId}")
    Admin getadminId(Long currentId);

    @Update("update admin set admin_number=#{adminNumber},name=#{name} where admin_id=#{adminId}")
    void updateAdmin(Admin admin);


}
