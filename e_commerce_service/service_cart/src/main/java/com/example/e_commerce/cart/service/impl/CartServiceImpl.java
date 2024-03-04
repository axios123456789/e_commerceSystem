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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 查询购物车列表功能
     * @return
     */
    @Override
    public List<CartInfo> getCartList() {
        //1.获取查询redis数据库的key值，根据userId构建
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.根据key查询到redis中的hash类型的所有value
        List<Object> values = redisTemplate.opsForHash().values(cartKey);

        //3.将查到的数据转换为最终需要的格式
        if (!CollectionUtils.isEmpty(values)){
            List<CartInfo> collect = values.stream()
                    .map(value -> JSON.parseObject(value.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());

            return collect;
        }

        return new ArrayList<>();
    }

    /**
     * 删除购物车中的商品
     * @param skuId
     */
    @Override
    public void deleteCart(Long skuId) {
        //1.获取redis中hash类型的key的值
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.在redis中根据key:cartKey, field: skuId删除购物车中的商品
        redisTemplate.opsForHash().delete(cartKey, String.valueOf(skuId));
    }

    /**
     * 更新购物车商品选中状态
     * @param skuId
     * @param isChecked
     */
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        //1.构建查询的redis里面hash类型的key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.判断key中是否包含对应的filed
        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if (hasKey){
            //3.根据key+filed取出对应的value
            String cartInfoStr = (String) redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

            //4.更新value的选中状态
            CartInfo cartInfo = JSON.parseObject(cartInfoStr, CartInfo.class);
            cartInfo.setIsChecked(isChecked);

            //5.将更新后的value重新存回到redis中去
            redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));
        }
    }

    /**
     * 更新购物车全部选中状态
     * @param isChecked
     */
    @Override
    public void allCheckCart(Integer isChecked) {
        //1.构建查询的redis里面的key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.根据key查询到全部的value
        List<Object> values = redisTemplate.opsForHash().values(cartKey);

        //3.判断查到的values是否为空
        if (!CollectionUtils.isEmpty(values)){
            //4.将values转换为list<CartInfo>
            List<CartInfo> collect = values.stream()
                    .map(value -> JSON.parseObject(value.toString(), CartInfo.class))
                    .collect(Collectors.toList());

            //5.遍历collect，更新选中状态，保存到redis数据库中
            collect.forEach(cartInfo -> {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cartKey, String.valueOf(cartInfo.getSkuId()), JSON.toJSONString(cartInfo));
            });
        }
    }

    /**
     * 清空购物车
     */
    @Override
    public void clearCart() {
        //1.构建查询的redis的hash类型的key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.根据cartKey清空redis里面hash类型的数据
        redisTemplate.delete(cartKey);
    }

    /**
     * 获取所有选中的购物车商品
     * @return
     */
    @Override
    public List<CartInfo> getAllChecked() {
        //1.构建redis中的key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.根据key查到所有values
        List<Object> values = redisTemplate.opsForHash().values(cartKey);

        if (!CollectionUtils.isEmpty(values)) {
            //3.将所有values中选中的购物车形成一个列表返回
            List<CartInfo> collect = values.stream().map(value -> JSON.parseObject(value.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .collect(Collectors.toList());
            return collect;
        }

        return new ArrayList<>();
    }

    /**
     * 删除生成订单的购物车商品
     */
    @Override
    public void deleteChecked() {
        //1.构建查询的redis的key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2.根据key查询所有values
        List<Object> values = redisTemplate.opsForHash().values(cartKey);

        //3.删除选中的商品
        if (!CollectionUtils.isEmpty(values)){
            values.stream().map(value -> JSON.parseObject(value.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey,
                            String.valueOf(cartInfo.getSkuId())));
        }
    }
}
