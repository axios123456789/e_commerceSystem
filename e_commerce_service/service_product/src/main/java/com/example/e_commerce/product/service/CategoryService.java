package com.example.e_commerce.product.service;

import com.example.e_commerce.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findOneCategory();   //查询所有一级分类
}
