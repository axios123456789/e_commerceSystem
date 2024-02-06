package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);    //品牌列表

    boolean save(Brand brand);  //添加品牌

    boolean update(Brand brand);    //品牌修改

    boolean deleted(Long id);   //逻辑删除品牌

    List<Brand> findAll();  //查询所以品牌
}
