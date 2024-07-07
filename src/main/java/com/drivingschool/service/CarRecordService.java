package com.drivingschool.service;

import com.drivingschool.pojo.entity.CarRecord;
import com.drivingschool.pojo.vo.CarRecordVO;

import java.util.List;

public interface CarRecordService {
    List<CarRecordVO> list(Long carId);

    void deleteById(Long recordId);
}
