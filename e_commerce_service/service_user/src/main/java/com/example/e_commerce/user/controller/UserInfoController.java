package com.example.e_commerce.user.controller;

import com.example.e_commerce.model.dto.h5.UserLoginDto;
import com.example.e_commerce.model.dto.h5.UserRegisterDto;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.h5.UserInfoVo;
import com.example.e_commerce.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    //获取当前登录用户信息
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }

    //用户登录
    @Operation(summary = "会员登录")
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto userLoginDto){
        String token = userInfoService.login(userLoginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
