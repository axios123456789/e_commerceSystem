package com.example.e_commerce.user.service;

public interface SmsService {
    void sendCode(String phone);    //发送短信验证码
}
