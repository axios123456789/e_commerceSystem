package com.example.e_commerce.order.mapper;

import com.example.e_commerce.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {
    //添加订单信息数据
    void save(OrderInfo orderInfo);
}
