package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.dto.product.CategoryBrandDto;
import com.example.e_commerce.model.entity.product.Brand;
import com.example.e_commerce.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto); //条件分页查询

    boolean save(CategoryBrand categoryBrand);  //添加

    boolean update(CategoryBrand categoryBrand);    //修改

    boolean deleteById(Long id);    //删除

    List<Brand> findBrandByCategoryId(Long categoryId); //根据分类id查询对应品牌数据
}
