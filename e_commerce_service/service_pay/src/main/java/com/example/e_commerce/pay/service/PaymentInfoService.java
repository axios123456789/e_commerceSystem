package com.example.e_commerce.pay.service;

import com.example.e_commerce.model.entity.pay.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService {
    PaymentInfo savePaymentInfo(String orderNo);    //保存支付记录

    void updatePaymentStatus(Map<String, String> paramMap); //更新交易记录状态
}
