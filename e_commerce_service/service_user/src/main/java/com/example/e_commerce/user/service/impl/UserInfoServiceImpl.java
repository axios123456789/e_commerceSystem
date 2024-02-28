package com.example.e_commerce.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.e_commerce.common_service.exception.MyException;
import com.example.e_commerce.common_util.AuthContextUtil;
import com.example.e_commerce.model.dto.h5.UserLoginDto;
import com.example.e_commerce.model.dto.h5.UserRegisterDto;
import com.example.e_commerce.model.entity.user.UserInfo;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.h5.UserInfoVo;
import com.example.e_commerce.user.mapper.UserInfoMapper;
import com.example.e_commerce.user.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    @Override
    public String login(UserLoginDto userLoginDto) {
        //1.获取登录信息
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        //2.根据用户名查询用户信息
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if (userInfo == null){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //3.比对登录的密码与查询到的用户信息密码是否一致
        String db_password = userInfo.getPassword();
        String md5_password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!db_password.equals(md5_password)){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4.校验用户是否被禁用
        if (userInfo.getStatus() == 0){
            throw new MyException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //5.生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //6.将用户信息保存到redis数据库中：key:token, value: userInfo
        redisTemplate.opsForValue().set("userInfo"+token, JSON.toJSONString(userInfo), 7, TimeUnit.DAYS);

        //7.返回token
        return token;
    }

    /**
     * 获取当前登录用户信息
     * @param token
     * @return
     */
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        /*//从redis中拿到相关数据
        String userdata = (String) redisTemplate.opsForValue().get("userInfo" + token);

        //判断拿到的数据是否为空
        if (!StringUtils.hasText(userdata)){
            throw new MyException(ResultCodeEnum.LOGIN_AUTH);
        }

        //将数据转换为对象
        UserInfo userInfo = JSON.parseObject(userdata, UserInfo.class);*/
        UserInfo userInfo = AuthContextUtil.getUserInfo();

        //将userinfo转换为对应的vo
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);

        return userInfoVo;
    }
}
