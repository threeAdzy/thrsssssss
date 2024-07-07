package com.drivingschool.service;

import com.drivingschool.common.result.PageResult;
import com.drivingschool.pojo.dto.CarAddDTO;
import com.drivingschool.pojo.dto.CarPageQueryDTO;
import com.drivingschool.pojo.dto.CarRepairsDTO;
import com.drivingschool.pojo.dto.CarUpdateDTO;
import com.drivingschool.pojo.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> list();

    PageResult pageQuery(CarPageQueryDTO carPageQueryDTO);

    void deleteById(Long carId);

    void save(CarAddDTO carAddDTO);

    void update(CarUpdateDTO carUpdateDTO);

    void updateState(CarUpdateDTO carUpdateDTO);

    void carRepairs(CarRepairsDTO carRepairsDTO);
}
