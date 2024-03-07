package com.example.e_commerce.order.service;

import com.example.e_commerce.model.dto.h5.OrderInfoDto;
import com.example.e_commerce.model.entity.order.OrderInfo;
import com.example.e_commerce.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

public interface OrderInfoService {
    TradeVo getTrade(); //结算

    Long submitOrder(OrderInfoDto orderInfoDto);    //生成订单

    OrderInfo getOrderInfo(Long orderId);   //获取订单信息

    TradeVo buy(Long skuId);    //立即购买

    PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus);    //获取订单分页列表
}
