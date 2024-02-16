package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.manager.mapper.ProductUnitMapper;
import com.example.e_commerce.manager.service.ProductUnitService;
import com.example.e_commerce.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper;

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}
