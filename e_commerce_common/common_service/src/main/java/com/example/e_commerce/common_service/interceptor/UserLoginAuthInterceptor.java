package com.example.e_commerce.common_service.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.e_commerce.common_util.AuthContextUtil;
import com.example.e_commerce.model.entity.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userJson = (String) redisTemplate.opsForValue().get("userInfo" + token);
        UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);
        AuthContextUtil.setUserInfo(userInfo);
        return true;
    }
}
