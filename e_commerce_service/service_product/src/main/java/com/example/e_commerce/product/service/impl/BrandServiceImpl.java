package com.example.e_commerce.product.service.impl;

import com.example.e_commerce.model.entity.product.Brand;
import com.example.e_commerce.product.mapper.BrandMapper;
import com.example.e_commerce.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询所有品牌
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }
}
