package com.example.e_commerce.product.mapper;

import com.example.e_commerce.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    //查询销量排行前十的商品
    List<ProductSku> selectProductSkuBySale();
}
