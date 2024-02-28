package com.example.e_commerce.user.service;

import com.example.e_commerce.model.dto.h5.UserLoginDto;
import com.example.e_commerce.model.dto.h5.UserRegisterDto;
import com.example.e_commerce.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto); //用户注册

    String login(UserLoginDto userLoginDto);    //用户登录

    UserInfoVo getCurrentUserInfo(String token);    //获取当前登录用户信息
}
