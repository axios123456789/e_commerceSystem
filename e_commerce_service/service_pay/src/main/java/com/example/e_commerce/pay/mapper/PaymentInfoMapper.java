package com.example.e_commerce.pay.mapper;

import com.example.e_commerce.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {
    //根据订单编号查询支付记录
    PaymentInfo getByOrderNo(String orderNo);

    //添加
    void save(PaymentInfo paymentInfo);

    //跟新支付信息
    void updatePaymentInfo(PaymentInfo paymentInfo);
}
