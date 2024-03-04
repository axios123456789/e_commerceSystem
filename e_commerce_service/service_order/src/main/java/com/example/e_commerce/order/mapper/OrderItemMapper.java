package com.example.e_commerce.order.mapper;

import com.example.e_commerce.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemMapper {
    //添加订单项数据
    void save(OrderItem orderItem);
}
