package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.dto.product.CategoryBrandDto;
import com.example.e_commerce.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    //条件分页查询
    List<CategoryBrand> selectAllByCondition(CategoryBrandDto categoryBrandDto);

    //添加
    void add(CategoryBrand categoryBrand);
}
