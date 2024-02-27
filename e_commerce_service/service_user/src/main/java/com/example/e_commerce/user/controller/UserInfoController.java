package com.example.e_commerce.user.controller;

import com.example.e_commerce.model.dto.h5.UserRegisterDto;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
