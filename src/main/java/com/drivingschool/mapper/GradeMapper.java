package com.drivingschool.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GradeMapper {
    @Insert("insert into grade ( user_id, classify) VALUES (#{userId},#{classify})")
    void insert(Long userId, String classify);
    @Delete("delete from grade where user_id = #{userId}")
    void deleteByUserId(Long userId);
}
