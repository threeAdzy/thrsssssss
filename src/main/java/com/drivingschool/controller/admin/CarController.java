package com.drivingschool.controller.admin;

import com.drivingschool.common.result.PageResult;
import com.drivingschool.common.result.Result;
import com.drivingschool.pojo.dto.CarAddDTO;
import com.drivingschool.pojo.dto.CarPageQueryDTO;
import com.drivingschool.pojo.dto.CarRepairsDTO;
import com.drivingschool.pojo.dto.CarUpdateDTO;
import com.drivingschool.pojo.entity.Car;
import com.drivingschool.service.CarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@RestController
@Slf4j
public class CarController implements Serializable {

    @Autowired
    private CarService carService;

    /**
     * 管理员汽车分页
     * @return
     */
    @GetMapping("/admin/car/page")
    public Result<PageResult> page(CarPageQueryDTO carPageQueryDTO) {

        log.info("汽车分页查询{}",carPageQueryDTO);
        PageResult pageResult=carService.pageQuery(carPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 教练选择汽车分页
     * @return
     */
    @GetMapping("/coach/car/page")
    public Result<PageResult> coachPage(CarPageQueryDTO carPageQueryDTO) {

        log.info("汽车选择分页查询{}",carPageQueryDTO);
        PageResult pageResult=carService.pageQuery(carPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 查车牌号
     * @return
     */
    //todo 返回list
    @GetMapping("/admin/carNumber")
    public Result<List<Car>> list(){
        List<Car> list=carService.list();
        return Result.success(list);
    }

    /**
     * 根据id删除汽车
     * @param carId
     * @return
     */
    @DeleteMapping("/admin/delcar")
    private Result deleteById(Long carId){
        log.info("根据id删除汽车，{}",carId);
        carService.deleteById(carId);
        return Result.success();
    }

    /**
     * 新增车辆
     * @param carAddDTO
     * @return
     */
    @PostMapping("/admin/addCar")
    public Result save(@RequestBody CarAddDTO carAddDTO){
        log.info("添加车辆{}",carAddDTO);
        carService.save(carAddDTO);
        return Result.success();
    }

    /**
     * 修改汽车信息为维修完成
     * @param carUpdateDTO
     * @return
     */
    @PostMapping("/admin/updateState")
    public Result updateState(@RequestBody CarUpdateDTO carUpdateDTO){
        log.info("维修改空闲，{}",carUpdateDTO);

        carService.updateState(carUpdateDTO);
        return Result.success();
    }

    /**
     * 修改汽车信息
     * @param carUpdateDTO
     * @return
     */
    @PostMapping("/admin/update")
    public Result update(@RequestBody CarUpdateDTO carUpdateDTO){
        log.info("修改汽车信息，{}",carUpdateDTO);

        carService.update(carUpdateDTO);
        return Result.success();
    }

    /**
     * 车辆报修
     * @param carRepairsDTO
     * @return
     */
    @PostMapping("/coach/updateCarState")
    public Result carRepairs(@RequestBody CarRepairsDTO carRepairsDTO){

        carService.carRepairs(carRepairsDTO);

    return Result.success();
}

}
