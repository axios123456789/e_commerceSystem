package com.example.e_commerce.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.example.e_commerce.common_service.exception.MyException;
import com.example.e_commerce.model.entity.pay.PaymentInfo;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.pay.service.AlipayService;
import com.example.e_commerce.pay.service.PaymentInfoService;
import com.example.e_commerce.pay.utils.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    /**
     * 支付宝下单
     * @param orderNo
     * @return
     */
    @Override
    public String submitAlipay(String orderNo) {
        //1.保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        //2.调用支付宝服务接口
        //构建需要参数，进行调用
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());
        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no",paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount",new BigDecimal("0.01"));
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()){
                String form = response.getBody();
                return form;
            }else {
                throw new MyException(ResultCodeEnum.DATA_ERROR);
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException();
        }
    }
}
