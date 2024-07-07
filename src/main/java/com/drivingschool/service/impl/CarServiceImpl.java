package com.drivingschool.service.impl;

import com.drivingschool.common.constant.StatusConstant;
import com.drivingschool.common.exception.CarStateException;
import com.drivingschool.common.exception.CoachIdException;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.mapper.CarMapper;
import com.drivingschool.mapper.CarRecordMapper;
import com.drivingschool.mapper.CoachMapper;
import com.drivingschool.pojo.dto.CarAddDTO;
import com.drivingschool.pojo.dto.CarPageQueryDTO;
import com.drivingschool.pojo.dto.CarRepairsDTO;
import com.drivingschool.pojo.dto.CarUpdateDTO;
import com.drivingschool.pojo.entity.Car;
import com.drivingschool.pojo.entity.CarRecord;
import com.drivingschool.pojo.entity.Coach;
import com.drivingschool.pojo.vo.CarPageQueryVO;
import com.drivingschool.service.CarService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CoachMapper coachMapper;
    @Autowired
    private CarRecordMapper carRecordMapper;

    /**
     * 分配车辆时显示汽车列表
     *
     * @return
     */
    @Override
    public List<Car> list() {
        return carMapper.list();
    }

    /**
     * 汽车分页查询
     *
     * @param carPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CarPageQueryDTO carPageQueryDTO) {
        if ((carPageQueryDTO.getCarNumber() != null && carPageQueryDTO.getCarNumber().length() != 0
        )||(carPageQueryDTO.getName()!=null &&carPageQueryDTO.getName().length()!=0)) {
            log.info("aaaaaaaaaaaaaaaa");
            carPageQueryDTO.setPage(1);
        }


        PageHelper.startPage(carPageQueryDTO.getPage(), carPageQueryDTO.getPageSize());

        Page<CarPageQueryVO> page = carMapper.pageQuery(carPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id删除汽车
     *
     * @param carId
     */
    @Override
    public void deleteById(Long carId) {
        int state = carMapper.selectByIdState(carId);
        if (state == 1) {
            throw new CarStateException("车辆正在使用中");
        } else {
            carMapper.deleteById(carId);
        }

    }

    /**
     * 新增车辆
     *
     * @param carAddDTO
     */
    @Override
    public void save(CarAddDTO carAddDTO) {

        Car car = new Car();
        car.setCarNumber("辽A·" + carAddDTO.getCarNumber() + "学");
        car.setCarImg(carAddDTO.getCarImg());

//        if (carAddDTO)
        if (carAddDTO.getCoachId() == null) {
            car.setCarState(StatusConstant.ENABLE);
            carMapper.insert(car);
        } else {
            Coach coach = new Coach();

//            car.setCoachId(carAddDTO.getCoachId());
            car.setCarState(StatusConstant.DISABLE);
            carMapper.insert(car);

            coach.setCoachId(carAddDTO.getCoachId());
            coach.setCarId(car.getCarId());

            coachMapper.update(coach);

            //todo 插入一个新的车辆使用记录
//            CarRecord carRecord = new CarRecord();
//            carRecord.setCoachId(coach.getCoachId());
//            carRecord.setCarId(car.getCarId());
//            carRecord.setUpdateTime(LocalDateTime.now());
            CarRecord carRecord = CarRecord.builder()
                    .coachId(coach.getCoachId())
                    .carId(car.getCarId())
                    .updateTime(LocalDateTime.now())
                    .build();
            carRecordMapper.insert(carRecord);
        }


    }

    /**
     * 修改车辆信息
     *
     * @param carUpdateDTO
     */
    @Override
    public void update(CarUpdateDTO carUpdateDTO) {
        Car car = new Car();
        BeanUtils.copyProperties(carUpdateDTO, car);
        car.setCarNumber("辽A·" + carUpdateDTO.getCarNumber() + "学");

        if (carUpdateDTO.getCarState() == 2 || carUpdateDTO.getCarState() == 0) {
            //更改成维修时


            //将教练表里的汽车id清空
            coachMapper.carIdNullByCarId(carUpdateDTO.getCarId());
            carMapper.update(car);
            //todo 已经不需要将车辆表的教练id置空（车辆表教练id属性已删除）
//            carMapper.updatestateTwo(carUpdateDTO.getCarId());
            return;
        }


        if (carUpdateDTO.getCarState() == 1) {
            //更改成使用时
            if (carUpdateDTO.getCoachId() == null) {

                throw new CoachIdException("状态为使用时，请为车辆分配教练");
            }
            carMapper.update(car);

            //去教练表找对应carid的教练并将carid置空
            coachMapper.carIdNullByCarId(car.getCarId());

            Coach coach = Coach.builder().coachId(carUpdateDTO.getCoachId()).carId(carUpdateDTO.getCarId()).build();

            coachMapper.update(coach);

            //todo 插入一个新的车辆使用记录
            CarRecord carRecord = new CarRecord();
            carRecord.setCoachId(coach.getCoachId());
            carRecord.setCarId(car.getCarId());
            carRecord.setUpdateTime(LocalDateTime.now());
            log.info("车辆使用记录{}", carRecord);
            carRecordMapper.insert(carRecord);

        }


    }

    /**
     * 维修改空闲
     *
     * @param carUpdateDTO
     */
    @Override
    public void updateState(CarUpdateDTO carUpdateDTO) {
        Car car = new Car();
        BeanUtils.copyProperties(carUpdateDTO, car);
        carMapper.updateStateZero(car);
    }

    /**
     * 车辆报修
     *
     * @param carRepairsDTO
     */
    @Override
    public void carRepairs(CarRepairsDTO carRepairsDTO) {
        //todo  车辆表中状态更改为维修 （车辆表教练id属性已删除）
        carMapper.carRepairs(carRepairsDTO.getCarId());
        //获取当前请求的教练id
//        carRepairsDTO.setCoachId(BaseContext.getCurrentId());
        //清除教练表的汽车id
        coachMapper.carIdNull(carRepairsDTO.getCoachId());
    }
}
