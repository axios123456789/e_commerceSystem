package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.dto.product.CategoryBrandDto;
import com.example.e_commerce.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto); //条件分页查询

    boolean save(CategoryBrand categoryBrand);  //添加
}
