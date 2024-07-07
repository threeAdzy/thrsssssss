package com.drivingschool.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "driving.jwt")
@Data
public class JwtProperties {

    /**
     * 管理端员生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 教练
     */
    private String coachSecretKey;
    private long coachTtl;
    private String coachTokenName;


    /**
     * 用户
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;


}
