package com.example.e_commerce.product.service;

import com.example.e_commerce.model.entity.product.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> findAll();  //查询所有品牌
}
