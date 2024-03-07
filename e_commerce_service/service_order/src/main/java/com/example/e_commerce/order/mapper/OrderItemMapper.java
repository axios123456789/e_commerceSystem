package com.example.e_commerce.order.mapper;

import com.example.e_commerce.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    //添加订单项数据
    void save(OrderItem orderItem);

    //根据订单id查询订单项
    List<OrderItem> findByOrderId(Long id);
}
