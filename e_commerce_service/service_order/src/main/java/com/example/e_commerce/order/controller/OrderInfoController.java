package com.example.e_commerce.order.controller;

import com.example.e_commerce.model.dto.h5.OrderInfoDto;
import com.example.e_commerce.model.entity.order.OrderInfo;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.h5.TradeVo;
import com.example.e_commerce.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/order/orderInfo")
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    //生成订单
    @Operation(summary = "提交订单")
    @PostMapping("/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "确认下单")
    @GetMapping("/auth/trade")
    public Result trade(){
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单信息")
    @GetMapping("/auth/{orderId}")
    public Result getOrderInfo(@PathVariable Long orderId){
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "立即购买")
    @GetMapping("/auth/buy/{skuId}")
    public Result buy(@PathVariable Long skuId){
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取订单分页列表")
    @GetMapping("/auth/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit,
                       @RequestParam(required = false, defaultValue = "") Integer orderStatus){
        PageInfo<OrderInfo> pageInfo = orderInfoService.findOrderPage(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //远程调用：根据订单编号获取订单信息
    @Operation(summary = "获取订单信息")
    @GetMapping("/auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@Parameter(name = "orderNo", description = "订单编号", required = true) @PathVariable String orderNo){
        return orderInfoService.getOrderInfoByOrderNo(orderNo);
    }
}
