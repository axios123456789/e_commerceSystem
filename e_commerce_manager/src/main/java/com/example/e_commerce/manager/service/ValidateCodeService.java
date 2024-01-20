package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.vo.system.ValidateCodeVo;

public interface ValidateCodeService {
    ValidateCodeVo generateValidateCode();  //生成验证码图片
}
