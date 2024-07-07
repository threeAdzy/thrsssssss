package com.drivingschool.mapper;

import com.drivingschool.pojo.entity.PassSubjects;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PassSubjectMapper {
    /**
     * 通过学员id查询该学员各科目通过情况
     *
     * @param userId
     * @return
     */
    @Select("select * from pass_subjects where user_id=#{userId}")
    PassSubjects passById(Long userId);

    @Insert("insert into pass_subjects (user_id) values (#{userId})")
    void insert(Long userId);

    void update(PassSubjects passSubjects);

    @Delete("DELETE FROM pass_subjects WHERE user_id = #{userId}")
    void deleteByUserId(Long userId);
}
