package com.example.e_commerce.product.mapper;

import com.example.e_commerce.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    //根据id查询商品信息
    Product getById(Long productId);
}
