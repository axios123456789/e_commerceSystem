package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoMapper {
    //查询前一天的订单统计数据
    OrderStatistics selectStatisticsByDate(String createDate);
}
