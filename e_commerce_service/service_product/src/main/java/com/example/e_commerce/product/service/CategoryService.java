package com.example.e_commerce.product.service;

import com.example.e_commerce.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findOneCategory();   //查询所有一级分类

    List<Category> findCategoryTree();  //查询所有分类，树形封装
}
