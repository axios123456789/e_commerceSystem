package com.example.e_commerce.user.service.impl;

import com.example.e_commerce.common_util.AuthContextUtil;
import com.example.e_commerce.model.entity.user.UserAddress;
import com.example.e_commerce.user.mapper.UserAddressMapper;
import com.example.e_commerce.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 获取用户地址列表
     * @return
     */
    @Override
    public List<UserAddress> findUserAddressList() {
        //获取用户id
        Long userId = AuthContextUtil.getUserInfo().getId();

        //根据用户Id查询用户地址列表
        List<UserAddress> userAddressList = userAddressMapper.getUserAddressListByUserId(userId);

        return userAddressList;
    }

    /**
     * 根据id获取收货地址信息
     * @param id
     * @return
     */
    @Override
    public UserAddress getUserAddress(Long id) {
        return userAddressMapper.getUserAddressById(id);
    }
}
