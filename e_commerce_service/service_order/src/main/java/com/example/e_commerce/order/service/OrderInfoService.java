package com.example.e_commerce.order.service;

import com.example.e_commerce.model.dto.h5.OrderInfoDto;
import com.example.e_commerce.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade(); //结算

    Long submitOrder(OrderInfoDto orderInfoDto);    //生成订单
}
