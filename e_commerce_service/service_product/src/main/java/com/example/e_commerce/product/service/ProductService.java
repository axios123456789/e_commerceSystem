package com.example.e_commerce.product.service;

import com.example.e_commerce.model.dto.h5.ProductSkuDto;
import com.example.e_commerce.model.entity.product.ProductSku;
import com.example.e_commerce.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale(); //查询销量排行前十的sku商品

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);  //分页查询商品Sku

    ProductItemVo item(Long skuId); //查看商品详细信息

    ProductSku getBySkuId(Long skuId);  //根据skuId查询sku信息
}
