package com.example.e_commerce.product.mapper;

import com.example.e_commerce.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    //根据product_id查询商品详细信息
    ProductDetails getByProductId(Long productId);
}
