package com.drivingschool.mapper;

import com.drivingschool.pojo.entity.CarRecord;
import com.drivingschool.pojo.vo.CarRecordVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarRecordMapper {
//    @Insert("INSERT INTO ")

    /**
     * 插入新记录
     * @param carRecord
     */
    void insert(CarRecord carRecord);
//    @Select("SELECT * FROM car_record WHERE car_id = #{carId}")

    /**
     * 根据车辆id获取纪录列表
     * @param carId
     * @return
     */
    List<CarRecordVO> list(Long carId);

    @Delete("DELETE FROM car_record WHERE record_id = #{recordId}")
    void deleteById(Long recordId);
}
