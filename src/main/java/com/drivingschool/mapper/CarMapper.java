package com.drivingschool.mapper;

import com.drivingschool.pojo.dto.CarPageQueryDTO;
import com.drivingschool.pojo.dto.CarUpdateDTO;
import com.drivingschool.pojo.entity.Car;
import com.drivingschool.pojo.vo.CarPageQueryVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarMapper {

    /**
     * 获取汽车列表
     * @return
     */
    @Select("SELECT * FROM car WHERE car_state = 0")
    List<Car> list();

    @Select("SELECT * FROM car WHERE car_id = #{carId}")
    Car  getById(Long carId);

    /**
     * 选车时将状态更新为使用
     *
     * @param carId
     */
//    @Update("UPDATE car SET car_state = 1,coach_id = #{coachId}  WHERE car_id = #{carId}")
//    void updatestate(Long carId, Long coachId);
    @Update("UPDATE car SET car_state = 1  WHERE car_id = #{carId}")
    void updatestate(Long carId);


    /**
     * 未选车时清除车辆状态和教练id，删除教练时也可用
     *
     * @param carId
     */
//    @Update("UPDATE car SET car_state = 0,coach_id = null  WHERE coach_id = #{caochId}")
//    void clearstate(Long caochId);
    @Update("UPDATE car SET car_state = 0  WHERE car_id = #{carId}")
    void clearstate(Long carId);

    /**
     * 汽车分页
     *
     * @param carPageQueryDTO
     * @return
     */
//    @Select("select *from car")
    Page<CarPageQueryVO> pageQuery(CarPageQueryDTO carPageQueryDTO);

    /**
     * 根据id删除汽车
     * @param carId
     */
    @Delete("delete from car where car_id = #{carId}")
    void deleteById(Long carId);

    /**
     * 根据id查询汽车状态
     * @param carId
     * @return
     */
    @Select("select car_state from car where car_id=#{carId}")
    int selectByIdState(Long carId);

//    @Insert("INSERT INTO car (car_number, car_img) VALUES (#{carNumber}, #{carImg})")
    void insert(Car car);


    /**
     * 前端传回2的时候更新为空闲
     * @param car
     */
    @Update("UPDATE car SET car_state = 0  WHERE car_id = #{carId}")
    void updateStateZero(Car car);

    void update(Car car);
//    @Update("UPDATE car SET coach_id=null  WHERE car_id = #{carId}")
//    void updatestateTwo(Long carId);

    /**
     * 车辆报修
     * @param carId
     */
    @Update("UPDATE car SET car_state=2  WHERE car_id = #{carId}")
    void carRepairs(Long carId);
//    void updatestateTwo(Long carId);
}


//
//    /**
//     * 状态更改为维修
//     * @param carId
//     */

//}
