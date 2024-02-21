package com.example.e_commerce.product.mapper;

import com.example.e_commerce.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //查询所有一级分类
    List<Category> selectOneCategory();
}
