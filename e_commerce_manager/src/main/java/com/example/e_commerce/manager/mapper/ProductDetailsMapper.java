package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductDetailsMapper {
    //添加商品详细信息
    void save(ProductDetails productDetails);

    //根据商品id查询商品详情
    ProductDetails getProductDetailsByProductId(Long id);

    //修改商品详情即product_details
    void updateById(ProductDetails productDetailsByProductId);

    //删除商品详情根据product_id
    @Update("update product_details set is_deleted = 1, update_time = now() where product_id = #{id}")
    void deleteByProductId(Long id);
}
