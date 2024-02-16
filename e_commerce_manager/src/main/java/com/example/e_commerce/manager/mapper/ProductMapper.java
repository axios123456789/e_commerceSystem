package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.dto.product.ProductDto;
import com.example.e_commerce.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper {
    //条件分页查询商品列表
    List<Product> findByCondition(ProductDto productDto);

    //保存商品
    void save(Product product);

    //根据id查询商品基本信息
    Product getProductById(Long id);

    //修改商品基本信息
    void updateById(Product product);

    //删除商品
    @Update("update product set is_deleted = 1, update_time = now() where id = #{id}")
    void deleteById(Long id);
}
