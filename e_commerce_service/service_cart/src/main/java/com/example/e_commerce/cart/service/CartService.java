package com.example.e_commerce.cart.service;

import com.example.e_commerce.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {
    void addToCart(Long skuId, Integer skuNum); //添加购物车

    List<CartInfo> getCartList();   //购物车列表

    void deleteCart(Long skuId);    //删除购物车中的商品

    void checkCart(Long skuId, Integer isChecked);  //更新购物车商品选中状态

    void allCheckCart(Integer isChecked);   //更新购物车全部选中状态

    void clearCart();   //清空购物车

    List<CartInfo> getAllChecked(); //获取所有选中的购物车商品

    void deleteChecked();   //删除生成订单的购物车商品
}
