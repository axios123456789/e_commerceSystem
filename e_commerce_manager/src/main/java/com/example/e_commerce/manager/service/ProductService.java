package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.dto.product.ProductDto;
import com.example.e_commerce.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);   //条件分页查询商品列表

    boolean save(Product product);  //商品添加

    Product getById(Long id);   //根据商品id查询商品信息

    boolean update(Product product);    //商品修改

    boolean deleteById(Long id);    //删除商品

    boolean updateAuditStatus(Long id, Integer auditStatus);    //审核

    boolean updateStatus(Long id, Integer status);  //上下架
}
