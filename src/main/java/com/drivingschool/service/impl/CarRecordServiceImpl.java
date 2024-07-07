package com.drivingschool.service.impl;

import com.drivingschool.mapper.CarRecordMapper;
import com.drivingschool.pojo.entity.CarRecord;
import com.drivingschool.pojo.vo.CarRecordVO;
import com.drivingschool.service.CarRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class CarRecordServiceImpl implements CarRecordService {
    @Autowired
    private CarRecordMapper carRecordMapper;
    @Override
    public List<CarRecordVO> list(Long carId) {
        return carRecordMapper.list(carId);
    }

    /**
     *
     * @param recordId
     */
    @Override
    public void deleteById(Long recordId) {
        carRecordMapper.deleteById(recordId);
    }
}
