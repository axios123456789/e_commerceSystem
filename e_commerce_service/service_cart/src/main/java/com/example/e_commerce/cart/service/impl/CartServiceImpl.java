package com.example.e_commerce.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.e_commerce.cart.service.CartService;
import com.example.e_commerce.common_util.AuthContextUtil;
import com.example.e_commerce.feign.product.ProductFeignClient;
import com.example.e_commerce.model.entity.h5.CartInfo;
import com.example.e_commerce.model.entity.product.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    //构建redis中hash类型key的名称
    private String getCartKey(Long userId){
        return "user:cart:" + userId;
    }

    /**
     * 添加购物车
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //1.必须登录的状态，获取当前登录的用户id作为redis的hash类型的key值
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.从redis中获取购物车数据，根据用户id + skuId获取（hash类型的key + field）
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

        //3.如果购物车存在添加的商品，则把商品数量相加
        CartInfo cartInfo = null;
        if (cartInfoObj != null){
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            //设置商品数量相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            //设置商品为选中状态
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }else {
            //4.如果不存在，直接把商品添加到购物车（redis里面），通过远程调用实现nacos + openFeign
            cartInfo = new CartInfo();

            //远程调用实现，根据skuId获取商品sku信息
            ProductSku productSku = productFeignClient.getBySkuId(skuId);

            //设置相关对象到cartInfo对象里
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }

        //将cartInfo添加到redis里面
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
    }
}
