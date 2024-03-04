package com.example.e_commerce.order.mapper;

import com.example.e_commerce.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogMapper {
    //添加订单log数据
    void save(OrderLog orderLog);
}
