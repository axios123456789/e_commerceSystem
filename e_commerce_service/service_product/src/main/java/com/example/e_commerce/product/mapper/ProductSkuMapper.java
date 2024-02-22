package com.example.e_commerce.product.mapper;

import com.example.e_commerce.model.dto.h5.ProductSkuDto;
import com.example.e_commerce.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    //查询销量排行前十的商品
    List<ProductSku> selectProductSkuBySale();

    //条件查询商品sku
    List<ProductSku> selectByCondition(ProductSkuDto productSkuDto);

    //根据id查询所有
    ProductSku getById(Long skuId);

    //根据product_id查询所有sku信息
    List<ProductSku> getByProductId(Long productId);
}
