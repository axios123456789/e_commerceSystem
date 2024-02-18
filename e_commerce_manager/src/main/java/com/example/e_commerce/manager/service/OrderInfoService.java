package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.dto.order.OrderStatisticsDto;
import com.example.e_commerce.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);    //订单统计
}
