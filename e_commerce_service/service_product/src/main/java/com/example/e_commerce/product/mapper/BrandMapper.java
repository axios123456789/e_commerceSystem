package com.example.e_commerce.product.mapper;

import com.example.e_commerce.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    //查询所有品牌
    List<Brand> selectAll();
}
