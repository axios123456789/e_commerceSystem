package com.example.e_commerce.product.service;

import com.example.e_commerce.model.entity.product.ProductSku;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale(); //查询销量排行前十的sku商品
}
