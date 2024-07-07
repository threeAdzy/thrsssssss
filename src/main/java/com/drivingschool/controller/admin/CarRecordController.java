package com.drivingschool.controller.admin;

import com.drivingschool.common.result.Result;
import com.drivingschool.pojo.vo.CarRecordVO;
import com.drivingschool.service.CarRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 车辆记录控制类
 */
@RestController
@Slf4j
public class CarRecordController {
    @Autowired
    private CarRecordService carRecordService;


    /**
     * 按车辆id查询车辆使用记录
     *
     * @param carId
     * @return
     */
    @GetMapping("/admin/carRecord")
    public Result<List<CarRecordVO>> list(Long carId) {
        List<CarRecordVO> list = carRecordService.list(carId);
        return Result.success(list);
    }
    /**
     * 根据id删除汽车
     * @param recordId
     * @return
     */
    @DeleteMapping("/admin/delRecord")
    private Result deleteById(Long recordId){
        log.info("根据id删除车辆记录，{}",recordId);
        carRecordService.deleteById(recordId);
        return Result.success();
    }

}
