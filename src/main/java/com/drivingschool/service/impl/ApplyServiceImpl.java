package com.drivingschool.service.impl;


import com.alibaba.fastjson.JSON;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.common.result.Result;
import com.drivingschool.mapper.*;
import com.drivingschool.pojo.dto.ApplyCoachDTO;
import com.drivingschool.pojo.dto.ApplyNoDTO;
import com.drivingschool.pojo.dto.ApplyUpdateDTO;
import com.drivingschool.pojo.dto.ApplypageDTO;
import com.drivingschool.pojo.entity.*;
import com.drivingschool.pojo.vo.ApplyPageVO;
import com.drivingschool.pojo.vo.CoachVO;
import com.drivingschool.pojo.vo.UserPageStudyVO;
import com.drivingschool.service.ApplyService;
import com.drivingschool.websocket.WebSocketServer;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请管理
 */
@Slf4j
@Transactional
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private CoachMapper coachMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CarRecordMapper carRecordMapper;
    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 教练新增申请
     *
     * @param applyCoachDTO
     */
    @Override
    public void coachApply(ApplyCoachDTO applyCoachDTO) {
        Apply apply = new Apply();
        BeanUtils.copyProperties(applyCoachDTO, apply);
        apply.setCreateTime(LocalDateTime.now());
        applyMapper.insertCoach(apply);

        CoachVO coach = coachMapper.getById(applyCoachDTO.getPersonId());
        Car car = carMapper.getById(applyCoachDTO.getContentId());

        Map map = new HashMap<>();
        map.put("type","车辆申请");//1表示车辆申请
        map.put("person", coach.getName());
        map.put("content",car.getCarNumber());

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);
    }

    /**
     * 学员申请更换教练
     *
     * @param applyCoachDTO
     */
    @Override
    public void userApply(ApplyCoachDTO applyCoachDTO) {
        Apply apply = new Apply();
        BeanUtils.copyProperties(applyCoachDTO, apply);
        apply.setCreateTime(LocalDateTime.now());
        applyMapper.insertUser(apply);


        Car car = carMapper.getById(applyCoachDTO.getContentId());

        User user = userMapper.getUserById(applyCoachDTO.getPersonId());

        CoachVO coachVO = coachMapper.getById(apply.getContentId());

        Map map = new HashMap<>();
        map.put("type","更换教练");
        map.put("person", user.getName());
        map.put("content",coachVO.getName());

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);
    }

    /**
     * 管理员分页查申请记录
     *
     * @param applypageDTO
     * @return
     */
    @Override
    public PageResult pageApply(ApplypageDTO applypageDTO) {
        if (applypageDTO.getPersonName() != null && applypageDTO.getPersonName().length() != 0
        ) {
            log.info("aaaaaaaaaaaaaaaa");
            applypageDTO.setPage(1);
        }
        PageHelper.startPage(applypageDTO.getPage(), applypageDTO.getPageSize());

        Page<ApplyPageVO> page = applyMapper.pageApply(applypageDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 统计未处理请求数量
     *
     * @param applyNoDTO
     * @return
     */
    @Override
    public Integer applyNo(ApplyNoDTO applyNoDTO) {

        Integer applyNo = applyMapper.applyNo(applyNoDTO);
        return applyNo;

    }

    @Override
    public void deleteById(Long applyId) {
        applyMapper.deleteById(applyId);
    }

    @Override
    public void update(ApplyUpdateDTO applyUpdateDTO) {
        if (applyUpdateDTO.getApplyState() == 2) {
            //2为驳回
            applyMapper.update(applyUpdateDTO);
        }
        if (applyUpdateDTO.getApplyState() == 1) {

            //等于1为教练换车
            if (applyUpdateDTO.getLv() == 1) {
                //查询教练是否有车辆
                CoachVO coachVO = coachMapper.getById(applyUpdateDTO.getPersonId());
                if (coachVO.getCarId() != null) {
                    //todo  清除当前车辆状态  无需将教练id置空（车辆表教练id已删除）
                    carMapper.clearstate(coachVO.getCarId());

                    //更新车辆记录的结束时间
                    //todo 会报错，因为现在有的车辆没有记录，不能只传时间，而且现在确定不了更改哪条记录
//                    carRecordMapper.update(LocalDateTime.now());
                }


                Coach coach = new Coach();
                coach.setCoachId(applyUpdateDTO.getPersonId());
                coach.setCarId(applyUpdateDTO.getContentId());
//                log.info("输出一下，{}", coach);

                coachMapper.update(coach);

                Car carNew = new Car();
                carNew.setCarId(applyUpdateDTO.getContentId());

//                carNew.setCoachId(applyUpdateDTO.getPersonId());
                carNew.setCarState(1);
                //将新分配的汽车状态改为使用
                carMapper.update(carNew);

                //todo 插入一个新的车辆使用记录
                CarRecord carRecord = new CarRecord();
                carRecord.setCoachId(coach.getCoachId());
                carRecord.setCarId(carNew.getCarId());
                carRecord.setUpdateTime(LocalDateTime.now());

                carRecordMapper.insert(carRecord);


                applyMapper.update(applyUpdateDTO);
            } else if (applyUpdateDTO.getLv() == 2) {
                //等于2为学员换教练
                User user = new User();
                user.setUserId(applyUpdateDTO.getPersonId());
                user.setCoachId(applyUpdateDTO.getContentId());
                userMapper.update(user);
                applyMapper.update(applyUpdateDTO);
            }

        }
    }
}
