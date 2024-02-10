package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    //查询所有商品规格
    List<ProductSpec> selectAll();

    //添加一条商品规格数据
    void add(ProductSpec productSpec);

    //修改商品规格
    void update(ProductSpec productSpec);

    //删除商品规格
    @Update("update product_spec set is_deleted = 1 where id = #{id}")
    void deleteById(Long id);
}
