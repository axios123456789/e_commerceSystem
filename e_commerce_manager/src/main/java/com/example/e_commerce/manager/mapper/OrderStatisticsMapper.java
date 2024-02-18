package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.dto.order.OrderStatisticsDto;
import com.example.e_commerce.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderStatisticsMapper {
    //添加查到的前一天数据到订单结果表中
    void insert(OrderStatistics orderStatistics);

    //查询所有订单统计结果
    List<OrderStatistics> selectAll(OrderStatisticsDto orderStatisticsDto);
}
