package com.example.e_commerce.user.service;

import com.example.e_commerce.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddressList();    //获取用户地址列表

    UserAddress getUserAddress(Long id);    //根据id获取收货地址信息
}
