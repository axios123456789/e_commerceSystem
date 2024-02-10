package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

public interface ProductSpecService {
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);  //条件分页查询商品规格

    boolean save(ProductSpec productSpec);  //添加商品规格

    boolean update(ProductSpec productSpec);    //修改商品规格

    boolean deleteById(Long id);    //删除商品规格
}
