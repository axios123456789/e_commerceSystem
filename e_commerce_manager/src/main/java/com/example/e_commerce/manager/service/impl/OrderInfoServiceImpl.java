package com.example.e_commerce.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.e_commerce.manager.mapper.OrderStatisticsMapper;
import com.example.e_commerce.manager.service.OrderInfoService;
import com.example.e_commerce.model.dto.order.OrderStatisticsDto;
import com.example.e_commerce.model.entity.order.OrderStatistics;
import com.example.e_commerce.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    /**
     * 订单统计
     * @param orderStatisticsDto
     * @return
     */
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        //条件查询所有订单统计结果
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectAll(orderStatisticsDto);

        //遍历orderStatisticsList集合，得到所有日期，把所有日期存到新集合里面
        List<String> dateList = orderStatisticsList.stream().map(orderStatistics ->
                DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .collect(Collectors.toList());

        //遍历orderStatisticsList集合，得到所有总金额，把总金额封装到新的List集合中
        List<BigDecimal> decimalList = orderStatisticsList.stream().map(OrderStatistics::getTotalAmount)
                .collect(Collectors.toList());

        //封装成相应的vo对象进行返回
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(decimalList);

        return orderStatisticsVo;
    }
}
