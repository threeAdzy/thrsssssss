package com.drivingschool.service.impl;

import com.drivingschool.common.constant.MessageConstant;
import com.drivingschool.common.constant.PasswordConstant;
import com.drivingschool.common.constant.StatusConstant;
import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.exception.*;
import com.drivingschool.common.result.PageResult;
import com.drivingschool.mapper.*;
import com.drivingschool.pojo.dto.*;
import com.drivingschool.pojo.entity.Evaluate;
import com.drivingschool.pojo.entity.PassSubjects;
import com.drivingschool.pojo.entity.User;
import com.drivingschool.pojo.vo.*;
import com.drivingschool.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    //科目表注入
    @Autowired
    private PassSubjectMapper passSubjectMapper;
    //练车时间表注入
    @Autowired
    private StudyMapper studyMapper;
    @Autowired
    private CoachMapper coachMapper;
    @Autowired
    private EvaluateMapper evaluateMapper;
    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 学员登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {

        String userNumber = userLoginDTO.getUserNumber();
        String password = userLoginDTO.getPassword();
        Integer lv = userLoginDTO.getLv();

        User user = userMapper.getUserNumber(userNumber, lv);

        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //todo 改密码有问题
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        log.info("传过来的密码MD5加密后，{}", password);
//
//        log.info("数据库查回来的密码，{}", user.getPassword());

        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (user.getState() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return user;
    }

    /**
     * 用户注册后自动登录
     *
     * @param userLoginDTO
     * @return
     */
    @Override
    public User autoLogin(UserLoginDTO userLoginDTO) {
        String userNumber = userLoginDTO.getUserNumber();
        Integer lv = userLoginDTO.getLv();

        User user = userMapper.getUserNumber(userNumber, lv);

        return user;
    }


    /**
     * 用户个人信息回显
     *
     * @param currentId
     * @return
     */
    @Override
    public UserInfoVO info(Long currentId) {
        UserInfoVO userInfoVO = userMapper.getUserInfoById(currentId);
        return userInfoVO;
    }

    /**
     * 用户更新个人信息
     *
     * @param userUpdateDTO
     */
    @Override
    public void updateSelf(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO, user);
        String pensonnelIdCard = userUpdateDTO.getIdentityCard();

        //截取身份证中出行人出生日期中的年、月、日
        Integer personYear = Integer.parseInt(pensonnelIdCard.substring(6, 10));
        Integer personMonth = Integer.parseInt(pensonnelIdCard.substring(10, 12));
        Integer personDay = Integer.parseInt(pensonnelIdCard.substring(12, 14));

        Calendar cal = Calendar.getInstance();
        // 得到当前时间的年、月、日
        Integer yearNow = cal.get(Calendar.YEAR);
        Integer monthNow = cal.get(Calendar.MONTH) + 1;
        Integer dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        Integer yearMinus = yearNow - personYear;
        Integer monthMinus = monthNow - personMonth;
        Integer dayMinus = dayNow - personDay;

        Integer age = yearMinus; //先大致赋值

        if (yearMinus == 0) { //出生年份为当前年份
            age = 0;
        } else { //出生年份大于当前年份
            if (monthMinus < 0) {//出生月份小于当前月份时，还没满周岁
                age = age - 1;
            }
            if (monthMinus == 0) {//当前月份为出生月份时，判断日期
                if (dayMinus < 0) {//出生日期小于当前月份时，没满周岁
                    age = age - 1;
                }
            }
        }

        if (age > 70 || age < 18) {
            throw new UserNotLoginException("您的年龄不符合相关规定。");
        }


//        log.info("数据库查回来的密码，{}",user.getPassword());

        if (userUpdateDTO.getPassword() != null && userUpdateDTO.getPassword() != "") {
            String password = DigestUtils.md5DigestAsHex(userUpdateDTO.getPassword().getBytes());

            log.info("传过来的密码MD5加密后，{}", password);
            user.setPassword(password);
        }
        userMapper.update(user);

    }

    /**
     * 管理分页查询学员
     *
     * @param userAdminPageDTO
     * @return
     */
    @Override
    public PageResult pageAdminUser(UserAdminPageDTO userAdminPageDTO) {
        if ((userAdminPageDTO.getUserName() != null && userAdminPageDTO.getUserName().length() != 0)||userAdminPageDTO.isAllsubjects()
        ) {
            log.info("aaaaaaaaaaaaaaaa");
            userAdminPageDTO.setPage(1);
        }

        PageHelper.startPage(userAdminPageDTO.getPage(), userAdminPageDTO.getPageSize());

        Page<UserAdminPageVO> page = userMapper.pageAdminUser(userAdminPageDTO);
        log.info("{}", page);

        return new PageResult(page.getTotal(), page.getResult());

    }

    /**
     * 更新学员账号状态
     *
     * @param userAdminStateDTO
     */
    @Override
    public void startOrStop(UserAdminStateDTO userAdminStateDTO) {
        User user = User.builder()
                .state(userAdminStateDTO.getState())
                .userId(userAdminStateDTO.getUserId())
                .build();
        userMapper.update(user);

    }

    /**
     * 新增用户
     *
     * @param userAdminAddDTO
     */
    @Override
    public void addUser(UserAdminAddDTO userAdminAddDTO) {
        User user = new User();
        BeanUtils.copyProperties(userAdminAddDTO, user);
        String pensonnelIdCard = userAdminAddDTO.getIdentityCard();

        //截取身份证中出行人出生日期中的年、月、日
        Integer personYear = Integer.parseInt(pensonnelIdCard.substring(6, 10));
        Integer personMonth = Integer.parseInt(pensonnelIdCard.substring(10, 12));
        Integer personDay = Integer.parseInt(pensonnelIdCard.substring(12, 14));

        Calendar cal = Calendar.getInstance();
        // 得到当前时间的年、月、日
        Integer yearNow = cal.get(Calendar.YEAR);
        Integer monthNow = cal.get(Calendar.MONTH) + 1;
        Integer dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        Integer yearMinus = yearNow - personYear;
        Integer monthMinus = monthNow - personMonth;
        Integer dayMinus = dayNow - personDay;

        Integer age = yearMinus; //先大致赋值

        if (yearMinus == 0) { //出生年份为当前年份
            age = 0;
        } else { //出生年份大于当前年份
            if (monthMinus < 0) {//出生月份小于当前月份时，还没满周岁
                age = age - 1;
            }
            if (monthMinus == 0) {//当前月份为出生月份时，判断日期
                if (dayMinus < 0) {//出生日期小于当前月份时，没满周岁
                    age = age - 1;
                }
            }
        }

        if (age > 70 || age < 18) {
            throw new UserNotLoginException("您的年龄不符合相关规定。");
        }


//todo 改密码设置
        if (userAdminAddDTO.getPassword() == null || userAdminAddDTO.getPassword() == "") {  //设置默认密码
            user.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        } else {
            String password = DigestUtils.md5DigestAsHex(userAdminAddDTO.getPassword().getBytes());
//            log.info("加密后密码，{}", password);
            user.setPassword(password);
        }

        userMapper.insert(user);
        passSubjectMapper.insert(user.getUserId());
        gradeMapper.insert(user.getUserId(), userAdminAddDTO.getClassify());

    }

    /**
     * 管理员更新用户信息
     *
     * @param userAdminUpdateDTO
     */
    @Override
    public void update(UserAdminUpdateDTO userAdminUpdateDTO) {
        User user = new User();
        PassSubjects passSubjects = new PassSubjects();
        BeanUtils.copyProperties(userAdminUpdateDTO, passSubjects);
        BeanUtils.copyProperties(userAdminUpdateDTO, user);
        String pensonnelIdCard = userAdminUpdateDTO.getIdentityCard();

        //截取身份证中出行人出生日期中的年、月、日
        Integer personYear = Integer.parseInt(pensonnelIdCard.substring(6, 10));
        Integer personMonth = Integer.parseInt(pensonnelIdCard.substring(10, 12));
        Integer personDay = Integer.parseInt(pensonnelIdCard.substring(12, 14));

        Calendar cal = Calendar.getInstance();
        // 得到当前时间的年、月、日
        Integer yearNow = cal.get(Calendar.YEAR);
        Integer monthNow = cal.get(Calendar.MONTH) + 1;
        Integer dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        Integer yearMinus = yearNow - personYear;
        Integer monthMinus = monthNow - personMonth;
        Integer dayMinus = dayNow - personDay;

        Integer age = yearMinus; //先大致赋值

        if (yearMinus == 0) { //出生年份为当前年份
            age = 0;
        } else { //出生年份大于当前年份
            if (monthMinus < 0) {//出生月份小于当前月份时，还没满周岁
                age = age - 1;
            }
            if (monthMinus == 0) {//当前月份为出生月份时，判断日期
                if (dayMinus < 0) {//出生日期小于当前月份时，没满周岁
                    age = age - 1;
                }
            }
        }

        if (age > 70 || age < 18) {
            throw new UserNotLoginException("您的年龄不符合相关规定。");
        }


        if (userAdminUpdateDTO.getPassword() != null && userAdminUpdateDTO.getPassword() != "") {
            user.setPassword(DigestUtils.md5DigestAsHex(userAdminUpdateDTO.getPassword().getBytes()));
        }
        userMapper.update(user);
        passSubjectMapper.update(passSubjects);

    }

    /**
     * 根据id删除用户
     *
     * @param userId
     */
    @Override
    public void deleteById(Long userId) {
        //删除用户表数据
        userMapper.deleteById(userId);
        //删除练习表数据
        studyMapper.deleteByUserId(userId);
        //删除科目表数据
        passSubjectMapper.deleteByUserId(userId);
        //删除驾照等级表数据
        gradeMapper.deleteByUserId(userId);

    }

    @Override
    public List<User> selectUserOfCoach(Long coachId) {
        return userMapper.selectUserOfCoach(coachId);
    }

    @Override
    public CoachVO myCoach(Long currentId) {
        //先获取该学员所属教练id
        User user = userMapper.getUserById(currentId);

        if (user.getCoachId() == null) {
            throw new CoachIdException("未选择教练，已自动跳转至教练选择页面");
        }

        CoachVO coachVO = coachMapper.getById(user.getCoachId());
        return coachVO;
    }

    /**
     * 学员评价教练
     *
     * @param userScoreDTO
     */
    @Override
    public void score(UserScoreDTO userScoreDTO) {
        //根据教练id和学员id先去表里查
        Evaluate evaluate = evaluateMapper.selectByUseridCoachid(userScoreDTO.getUserId(), userScoreDTO.getCoachId());
        if (evaluate != null) {
            evaluateMapper.updateScore(userScoreDTO);
        } else {
            evaluateMapper.insertScore(userScoreDTO);
        }
    }

    /**
     * 学员选择教练
     *
     * @param userScoreDTO
     */
    @Override
    public void selectCoach(UserScoreDTO userScoreDTO) {

        User user = new User();

        user.setCoachId(userScoreDTO.getCoachId());
        user.setUserId(userScoreDTO.getUserId());
        userMapper.update(user);

    }


    /**
     * 教练查询学员分页
     *
     * @param userPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {

        if (userPageQueryDTO.getName() != null && userPageQueryDTO.getName().length() != 0
        ) {
            log.info("aaaaaaaaaaaaaaaa");
            userPageQueryDTO.setPage(1);
        }

        PageHelper.startPage(userPageQueryDTO.getPage(), userPageQueryDTO.getPageSize());

        Page<UserPageQueryVO> page = userMapper.pageQuery(userPageQueryDTO);


        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 学员传时间分页
     *
     * @param userPageStudyDTO
     * @return
     */
    @Override
    public PageResult pageStudyQuery(UserPageStudyDTO userPageStudyDTO) {

        if (userPageStudyDTO.getName() != null && userPageStudyDTO.getName().length() != 0
        ) {
            log.info("aaaaaaaaaaaaaaaa");
            userPageStudyDTO.setPage(1);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        PageHelper.startPage(userPageStudyDTO.getPage(), userPageStudyDTO.getPageSize());

//
        Date dateBegin = null;
        Date dateEnd = null;
        String begin = null, end = null;
        UserAddDate userAddDate = UserAddDate.builder()
                .page(userPageStudyDTO.getPage())
                .pageSize(userPageStudyDTO.getPageSize())
                .coachId(userPageStudyDTO.getCoachId())
//                .studyId(userPageStudyDTO.getStudyId())
                .userId(userPageStudyDTO.getUserId())
                .name(userPageStudyDTO.getName())
                .sex(userPageStudyDTO.getSex())
                .subject(userPageStudyDTO.getSubject())
                .classify(userPageStudyDTO.getClassify())
                .build();

//        System.out.println(dateBegin);

//        Long time = System.currentTimeMillis();  //获取当前时间

        //判断开始时间
        if (userPageStudyDTO.getBegin() != null) {
            begin = format.format(userPageStudyDTO.getBegin());

            try {
                dateBegin = format.parse(begin);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

//            userAddDate = UserAddDate.builder().begin(dateBegin).build();
            userAddDate.setBegin(dateBegin);
        }
        //判断结束时间
        if (userPageStudyDTO.getEnd() != null) {
            Long ea = userPageStudyDTO.getEnd() + 86400000;
            end = format.format(ea);
            try {
                dateEnd = format.parse(end);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
//            userAddDate = UserAddDate.builder().begin(dateBegin).end(dateEnd).build();
            userAddDate.setEnd(dateEnd);
        }


        System.out.println(begin);
        System.out.println(end);


//        date=formatter.parse(time);
//        try {
//            dateBegin = format.parse(begin);
//            dateEnd = format.parse(end);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }


//        .build();

//1710864000000    1711641600000

        Page<UserPageStudyVO> page = userMapper.pageAddDate(userAddDate);


//        Page<UserPageStudyVO> page = userMapper.pageStudyQuery(userPageStudyDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 获取学员列表
     *
     * @return
     */
    @Override
    public List<User> list() {

        Long coachId = BaseContext.getCurrentId();
        return userMapper.list(coachId);
    }

    @Override
    public User getUserById(Long userId) {
        User user = userMapper.getUserById(userId);
        return user;
    }


}
