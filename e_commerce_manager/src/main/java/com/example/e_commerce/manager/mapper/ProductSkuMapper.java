package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    //添加sku
    void save(ProductSku productSku);

    //根据商品id查询sku信息
    List<ProductSku> getProductSkuListByProductId(Long id);

    //修改商品sku信息即product_sku
    void updateById(ProductSku productSku);

    //删除sku根据product_id
    @Update("update product_sku set is_deleted = 1, update_time = now() where product_id = #{id}")
    void deleteByProductId(Long id);
}
