package com.example.e_commerce.user.service.impl;

import com.example.e_commerce.common_service.exception.MyException;
import com.example.e_commerce.model.dto.h5.UserRegisterDto;
import com.example.e_commerce.model.entity.user.UserInfo;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.user.mapper.UserInfoMapper;
import com.example.e_commerce.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     * @param userRegisterDto
     */
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //1.得到相关UserRegisterDto数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        //2.验证用户名，用户名不能重复
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if (userInfo != null){
            throw new MyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //3.验证码验证，与数据库的验证码一致
        String redisCode = (String) redisTemplate.opsForValue().get(username);
        if (!redisCode.equals(code)){
            throw new MyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //4.封装UserInfo数据
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        //5.将数据存到user_info表中
        userInfoMapper.save(userInfo);

        //删除redis数据库中的验证码数据
        redisTemplate.delete(username);
    }
}
