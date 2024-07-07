package com.drivingschool.interceptor;

import com.drivingschool.common.constant.JwtClaimsConstant;
import com.drivingschool.common.context.BaseContext;
import com.drivingschool.common.properties.JwtProperties;
import com.drivingschool.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@Slf4j
public class JwtTokenCoachInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getCoachTokenName());

        //2、校验令牌
        try {
            log.info("教练jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getCoachSecretKey(), token);
            Long allId = Long.valueOf(claims.get(JwtClaimsConstant.COACH_ID).toString());
            log.info("当前教练id：{}", allId);
            BaseContext.setCurrentId(allId);


            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}