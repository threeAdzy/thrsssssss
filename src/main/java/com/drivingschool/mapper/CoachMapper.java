package com.drivingschool.mapper;

import com.drivingschool.pojo.dto.CoachPageQueryDTO;
import com.drivingschool.pojo.entity.Coach;
import com.drivingschool.pojo.vo.CoachScorePageVO;
import com.drivingschool.pojo.vo.CoachVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoachMapper {


     /**
      * 教练分页查询
      * @param coachPageQueryDTO
      * @return
      */
     Page<CoachVO> pageQuery(CoachPageQueryDTO coachPageQueryDTO);

//    @Insert("INSERT INTO coach (coach_number, name, password, age, sex, phone,  car_id, coach_img) " +
//            "VALUES (#{coachNumber}, #{name}, #{password}, #{age}, #{sex}, #{phone}, #{carId}, #{coachImg})")
    void insert(Coach coach);

    /**
     * 根据教练id进行数据回显
     *
     * @param coachId
     * @return
     */
    @Select("SELECT c.*, car.car_number,car.car_img " +
            "FROM coach c " +
            "LEFT JOIN car car ON c.car_id = car.car_id " +
            "WHERE c.coach_id = #{coachId}")
    CoachVO getById(Long coachId);

    /**
     * 更新教练信息
     * @param coach
     */
    void update(Coach coach);

    /**
     * 教练登录
     * @param coachNumber
     * @param lv
     * @return
     */
    @Select("select * from coach where coach_number=#{coachNumber} and lv=#{lv}")
    Coach getcoachNumber(String coachNumber, Integer lv);

    @Update("UPDATE coach SET car_id = null  WHERE coach_id = #{coachId}")
    void carIdNull(Long coachId);

    /**
     * 获取教练列表
     * @return
     */
    @Select("select * from coach where car_id is null ")
    List<Coach> list();

    @Update("UPDATE coach SET car_id = null  WHERE car_id = #{carId}")
    void carIdNullByCarId(Long carId);
    @Select("select * from coach where state=0")
    List<Coach> listOfUser();
    @Delete("DELETE FROM coach WHERE coach_id = #{coachId}")
    void deleteById(Long coachId);

    Page<CoachScorePageVO> pageQScore(CoachPageQueryDTO coachPageQueryDTO);
}
