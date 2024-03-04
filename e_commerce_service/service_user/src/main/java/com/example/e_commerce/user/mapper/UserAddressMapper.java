package com.example.e_commerce.user.mapper;

import com.example.e_commerce.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    //根据用户id查询用户地址列表
    List<UserAddress> getUserAddressListByUserId(Long userId);

    //根据id获取收货地址信息
    UserAddress getUserAddressById(Long id);
}
