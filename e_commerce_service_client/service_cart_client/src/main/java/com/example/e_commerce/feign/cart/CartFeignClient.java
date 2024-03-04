package com.example.e_commerce.feign.cart;

import com.example.e_commerce.model.entity.h5.CartInfo;
import com.example.e_commerce.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "service-cart")
public interface CartFeignClient {
    //远程调用：删除生成订单的购物车中的商品
    @GetMapping("/api/order/cart/auth/deleteChecked")
    Result deleteChecked();

    //订单结算使用，获取购物车中选中的商品
    @GetMapping("/api/order/cart/auth/getAllChecked")
    List<CartInfo> getAllChecked();
}
