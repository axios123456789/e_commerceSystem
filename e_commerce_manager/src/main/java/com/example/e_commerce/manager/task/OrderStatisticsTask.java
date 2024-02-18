package com.example.e_commerce.manager.task;

import cn.hutool.core.date.DateUtil;
import com.example.e_commerce.manager.mapper.OrderInfoMapper;
import com.example.e_commerce.manager.mapper.OrderStatisticsMapper;
import com.example.e_commerce.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderStatisticsTask {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    //每天凌晨两点，查询前一天订单统计数据，把统计之后的数据添加到订单统计结果表里面
    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics(){
        //获取前一天日期
        String createDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");

        //根据前一天日期进行统计订单数以及订单总金额
        OrderStatistics orderStatistics = orderInfoMapper.selectStatisticsByDate(createDate);

        //将统计到的数据加到订单统计结果表里面
        if (orderStatistics != null){
            orderStatisticsMapper.insert(orderStatistics);
        }
    }
}
