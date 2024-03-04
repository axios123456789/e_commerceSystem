package com.example.e_commerce.order.service.impl;

import com.example.e_commerce.common_service.exception.MyException;
import com.example.e_commerce.common_util.AuthContextUtil;
import com.example.e_commerce.feign.cart.CartFeignClient;
import com.example.e_commerce.feign.product.ProductFeignClient;
import com.example.e_commerce.feign.user.UserFeignClient;
import com.example.e_commerce.model.dto.h5.OrderInfoDto;
import com.example.e_commerce.model.entity.h5.CartInfo;
import com.example.e_commerce.model.entity.order.OrderInfo;
import com.example.e_commerce.model.entity.order.OrderItem;
import com.example.e_commerce.model.entity.order.OrderLog;
import com.example.e_commerce.model.entity.product.ProductSku;
import com.example.e_commerce.model.entity.user.UserAddress;
import com.example.e_commerce.model.entity.user.UserInfo;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.h5.TradeVo;
import com.example.e_commerce.order.mapper.OrderInfoMapper;
import com.example.e_commerce.order.mapper.OrderItemMapper;
import com.example.e_commerce.order.mapper.OrderLogMapper;
import com.example.e_commerce.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    /**
     * 结算
     * @return
     */
    @Override
    public TradeVo getTrade() {
        //远程调用：获取购物车中的商品列表
        List<CartInfo> allChecked = cartFeignClient.getAllChecked();

        //创建商品数据项的集合,并封装相应数据到里面
        List<OrderItem> orderItemList = new ArrayList<>();
        allChecked.forEach(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        });

        //获取订单支付总金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList){
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }

        //创建返回给前端的vo对象, 并封装数据
        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalAmount);

        return tradeVo;
    }

    /**
     * 生成订单
     * @param orderInfoDto
     * @return
     */
    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        //1.从OrderInfoDto中获得订单项List<OrderItem>
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();

        //2.判断List<OrderItem>如果为空，抛出异常
        if (CollectionUtils.isEmpty(orderItemList)){
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }

        //3.校验商品库存是否充足
        //遍历List<OrderItem>集合，得到每个OrderItem
        for (OrderItem orderItem : orderItemList){
            //远程调用获取商品sku信息，（包含库存量）
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if (productSku == null){
                throw new MyException(ResultCodeEnum.DATA_ERROR);
            }

            //校验每个orderItem库存量是否充足
            if (orderItem.getSkuNum().intValue() > productSku.getStockNum().intValue()){
                throw new MyException(ResultCodeEnum.STOCK_LESS);
            }
        }

        //4.添加数据到order_info表
        //封装数据到orderInfo对象
        OrderInfo orderInfo = new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());

        //封装收货地址信息
        Long userAddressId = orderInfoDto.getUserAddressId();
        // 远程调用：获取用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(userAddressId);
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);

        orderInfoMapper.save(orderInfo);

        //5.添加数据到order_item表
        //添加List<OrderItem>里面数据，把集合中每个orderItem添加到表中
        for (OrderItem orderItem : orderItemList){
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }

        //6.添加数据到order_log表
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        //7.远程调用：把生成订单商品从购物车中删除
        cartFeignClient.deleteChecked();

        //8.返回订单id
        return orderInfo.getId();
    }
}
