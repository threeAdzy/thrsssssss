create table driving_school.admin
(
    admin_id     bigint auto_increment comment '管理员id'
        primary key,
    admin_number varchar(20)   null comment '管理员账号，用于登录',
    password     varchar(64)   not null comment '管理员密码，用于登录',
    name         varchar(10)   null comment '管理员姓名',
    lv           int default 0 not null comment '权限等级 0管理员 1教练 2用户',
    constraint admin_number
        unique (admin_number)
)
    comment '管理员表';

create table driving_school.apply
(
    apply_id    bigint auto_increment comment '申请表id'
        primary key,
    person_id   bigint        not null comment '申请人id,学员、教练均可',
    lv          int           not null comment '申请人权限，用于区分申请人是学员还是教练,同时区别需要更改的内容id的表',
    admin_id    bigint        null comment '处理人id',
    content_id  bigint        not null comment '申请的内容id，学员申请为更换教练id，教练申请为更换车辆id。',
    apply_state int default 0 not null comment '请求的状态，0为未处理，1为同意，2为驳回',
    create_time datetime      not null comment '记录申请创建的时间',
    update_time datetime      null comment '记录管理员处理申请的时间'
)
    comment '申请表';

create table driving_school.car
(
    car_id     bigint auto_increment comment '车辆id'
        primary key,
    car_number varchar(10)   not null comment '车牌号唯一非空',
    car_state  int default 0 null comment '车辆状态，0空闲 1使用 2维修',
    car_img    varchar(255)  null comment '汽车照片',
    constraint car_pk2
        unique (car_number)
)
    comment '车辆表';

create table driving_school.car_record
(
    record_id   bigint auto_increment comment '车辆使用记录编号'
        primary key,
    car_id      bigint   not null comment '车辆id',
    coach_id    bigint   not null comment '教练id',
    update_time datetime not null comment '更新时间',
    constraint record_id
        unique (record_id)
)
    comment '车辆使用记录表';

create table driving_school.coach
(
    coach_id     bigint auto_increment comment '教练id'
        primary key,
    coach_number varchar(20)   not null comment '教练账号',
    name         varchar(10)   not null comment '教练姓名',
    password     varchar(64)   not null comment '教练密码',
    age          int           not null comment '教练年龄',
    sex          int           null comment '教练性别 0男 1女',
    phone        varchar(11)   null comment '教练电话',
    lv           int default 1 not null comment '权限 0管理员 1教练 2用户',
    state        int default 0 not null comment '账号状态，0启用 1禁用',
    car_id       bigint        null comment '教练被分配的车辆id',
    coach_img    varchar(255)  null comment '教练头像',
    constraint coach_pk2
        unique (coach_number)
)
    comment '教练表';

create table driving_school.evaluate
(
    evaluate_id bigint auto_increment comment '评价表id'
        primary key,
    user_id     bigint not null comment '用户id',
    coach_id    bigint not null comment '被评价的教练id',
    score       int    null comment '评分，1-5'
)
    comment '评价表';

create table driving_school.grade
(
    grade_id bigint auto_increment comment '驾照等级选择表id'
        primary key,
    user_id  bigint                  not null comment '用户id',
    classify varchar(2) default 'C1' not null
)
    comment '驾照等级表';

create table driving_school.pass_subjects
(
    subject_id    bigint auto_increment comment '科目表id'
        primary key,
    user_id       bigint        not null comment '用户id',
    subject_one   int default 0 not null comment '科目一 0未通过 1通过',
    subject_two   int default 0 not null comment '科目二 0未通过 1通过',
    subject_three int default 0 not null comment '科目三 0未通过 1通过',
    subject_four  int default 0 not null comment '科目四 0未通过 1通过',
    constraint subject_id
        unique (subject_id)
)
    comment '科目表';

create table driving_school.study
(
    study_id   bigint auto_increment comment '练习id'
        primary key,
    user_id    bigint   not null comment '用户的id',
    coach_id   bigint   not null comment '教练id',
    start_time datetime not null comment '练习开始时间',
    end_time   datetime null comment '练习结束时间',
    subject    int      null comment '练习的科目,2为科目二，3为科目三'
)
    comment '学车时间表';

create table driving_school.user
(
    user_id       bigint auto_increment comment '用户id'
        primary key,
    user_number   varchar(20)   not null comment '用户账号，用于登录',
    name          varchar(10)   not null comment '用户姓名',
    password      varchar(64)   not null comment '用户密码，用于登录',
    identity_card varchar(18)   not null comment '身份证号',
    sex           int           null comment '性别 0男 1女',
    phone         varchar(11)   null comment '电话',
    lv            int default 2 not null comment '权限 0管理员 1教练 2用户',
    state         int default 0 null comment '0启用 1禁用',
    coach_id      bigint        null comment '所属教练id',
    user_img      varchar(255)  null comment '用户头像',
    constraint user_pk2
        unique (user_number),
    constraint user_pk3
        unique (identity_card)
)
    comment '用户表';


