package com.example.e_commerce.order.mapper;

import com.example.e_commerce.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    //添加订单信息数据
    void save(OrderInfo orderInfo);

    //获取订单信息
    OrderInfo getOrderInfoById(Long orderId);

    //根据用户id和状态查询
    List<OrderInfo> findUserPage(Long userId, Integer orderStatus);
}
