package com.example.e_commerce.user.mapper;

import com.example.e_commerce.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    //根据用户名查询用户数据
    UserInfo selectByUsername(String username);

    //保存数据
    void save(UserInfo userInfo);
}
