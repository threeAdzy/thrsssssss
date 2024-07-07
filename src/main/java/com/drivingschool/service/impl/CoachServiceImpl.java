package com.drivingschool.service.impl;

import com.drivingschool.common.constant.MessageConstant;
import com.drivingschool.common.constant.PasswordConstant;
import com.drivingschool.common.constant.StatusConstant;
import com.drivingschool.common.exception.AccountLockedException;
import com.drivingschool.common.exception.AccountNotFoundException;
import com.drivingschool.common.exception.PasswordErrorException;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.mapper.CarMapper;
import com.drivingschool.mapper.CoachMapper;
import com.drivingschool.mapper.StudyMapper;
import com.drivingschool.mapper.UserMapper;
import com.drivingschool.pojo.dto.CoachDTO;
import com.drivingschool.pojo.dto.CoachLoginDTO;
import com.drivingschool.pojo.dto.CoachPageQueryDTO;
import com.drivingschool.pojo.entity.Coach;
import com.drivingschool.pojo.vo.CoachScorePageVO;
import com.drivingschool.pojo.vo.CoachVO;
import com.drivingschool.service.CoachService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Transactional
@Slf4j
@Service
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachMapper coachMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudyMapper studyMapper;

    /**
     * 教练分页查询
     *
     * @param coachPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CoachPageQueryDTO coachPageQueryDTO) {
//        log.info("{}", coachPageQueryDTO.getName().length());

        if (coachPageQueryDTO.getName() != null && coachPageQueryDTO.getName().length() != 0
        ) {
            log.info("aaaaaaaaaaaaaaaa");
            coachPageQueryDTO.setPage(1);
        }

        PageHelper.startPage(coachPageQueryDTO.getPage(), coachPageQueryDTO.getPageSize());

        Page<CoachVO> page = coachMapper.pageQuery(coachPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 教练评分分页
     *
     * @param coachPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageScore(CoachPageQueryDTO coachPageQueryDTO) {
        //todo   页码变为1
        if (coachPageQueryDTO.getName() != null && coachPageQueryDTO.getName().length() != 0
        ) {
            log.info("aaaaaaaaaaaaaaaa");
            coachPageQueryDTO.setPage(1);
        }
        PageHelper.startPage(coachPageQueryDTO.getPage(), coachPageQueryDTO.getPageSize());

        Page<CoachScorePageVO> page = coachMapper.pageQScore(coachPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }


    /**
     * 新增教练
     *
     * @param coachDTO
     */
    @Override
    public void save(CoachDTO coachDTO) {
        Coach coach = new Coach();
//拷贝对象
        BeanUtils.copyProperties(coachDTO, coach);

//        if (coachDTO.getCarId() != null) {
//            carMapper.updatestate(coachDTO.getCarId(),coach.getCoachId());
//        }


        if (coachDTO.getPassword() == null || coachDTO.getPassword() == "") {  //设置默认密码
            coach.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        } else {
            coach.setPassword(DigestUtils.md5DigestAsHex(coachDTO.getPassword().getBytes()));
        }

        coachMapper.insert(coach);

        if (coachDTO.getCarId() != null) {
            //todo 已经不需要为车辆表的教练id赋值（车辆表教练id属性已删除）
//            carMapper.updatestate(coachDTO.getCarId(), coach.getCoachId());
            carMapper.updatestate(coachDTO.getCarId());
        }
    }

    /**
     * 通过id查询教练
     *
     * @param coachId
     * @return
     */
    @Override
    public CoachVO getById(Long coachId) {
        CoachVO coachVO = coachMapper.getById(coachId);


        return coachVO;
    }

    /**
     * 编辑更新教练
     *
     * @param coachDTO
     */
    @Override
    public void update(CoachDTO coachDTO) {
        Coach coach = new Coach();
        BeanUtils.copyProperties(coachDTO, coach);
        if (coachDTO.getPassword() != null && coachDTO.getPassword() != "") {
            coach.setPassword(DigestUtils.md5DigestAsHex(coachDTO.getPassword().getBytes()));
        }


        if (coachDTO.getCarId() != null) {
            //todo 已经不需要为车辆表的教练id赋值（车辆表教练id属性已删除）
            carMapper.clearstate(coachDTO.getCarId());

            //todo 已经不需要为车辆表的教练id赋值（车辆表教练id属性已删除）
            carMapper.updatestate(coachDTO.getCarId());
            coachMapper.update(coach);
//            System.out.println("前端传回来的时候有汽车id走这AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            return;
        } else if (coachMapper.getById(coachDTO.getCoachId()).getCarId() != null) {
            coach.setCarId(coachMapper.getById(coachDTO.getCoachId()).getCarId());
            coachMapper.update(coach);
//            System.out.println("前端没传车，但是教练本来有车的走这BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

            return;


        }


//        if (coachDTO.getCarId() != null) {
//
//            carMapper.clearstate(coachDTO.getCoachId());
//            carMapper.updatestate(coachDTO.getCarId(), coach.getCoachId());
//        } else {
//
//
//            carMapper.clearstate(coachDTO.getCoachId());
//            coachMapper.carIdNull(coach.getCoachId());
//
//        }

        coachMapper.update(coach);
    }

    /**
     * 更改账号状态
     *
     * @param coachDTO
     */
    @Override
    public void startOrStop(CoachDTO coachDTO) {
        Coach coach = Coach.builder()
                .state(coachDTO.getState())
                .coachId(coachDTO.getCoachId())
                .build();
        coachMapper.update(coach);
    }


    /**
     * 教练登录
     *
     * @param coachLoginDTO
     * @return
     */
    @Override
    public Coach login(CoachLoginDTO coachLoginDTO) {

        String coachNumber = coachLoginDTO.getCoachNumber();
        String password = coachLoginDTO.getPassword();
        Integer lv = coachLoginDTO.getLv();

        Coach coach = coachMapper.getcoachNumber(coachNumber, lv);
        if (coach == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(coach.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (coach.getState() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        return coach;
    }

    @Override
    public CoachVO info(Long currentId) {
        CoachVO coachVO = coachMapper.getById(currentId);
        return coachVO;
    }

    @Override
    public List<Coach> list() {
        return coachMapper.list();
    }

    @Override
    public List<Coach> listOfUser() {
        return coachMapper.listOfUser();
    }

    /**
     * 通过教练id删除教练
     *
     * @param coachId
     */
    @Override
    public void deleteById(Long coachId) {
        //跟教练id获取该车辆id
        CoachVO coachVO = coachMapper.getById(coachId);

        //教练表里删除
        coachMapper.deleteById(coachId);
        //汽车表里把教练id置空
        //todo  清除当前车辆状态  将教练id置空（车辆表教练id已删除）
        carMapper.clearstate(coachVO.getCarId());
        //用户表里教练id置空
        userMapper.updateCoach(coachId);
        //练习表里删除
        studyMapper.deleteByCoachId(coachId);
    }


}
