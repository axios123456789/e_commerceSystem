package com.example.e_commerce.product.service.impl;

import com.example.e_commerce.model.entity.product.ProductSku;
import com.example.e_commerce.product.mapper.ProductSkuMapper;
import com.example.e_commerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    /**
     * 查询销量前十的sku商品
     * @return
     */
    @Override
    public List<ProductSku> findProductSkuBySale() {
        List<ProductSku> productSkuList = productSkuMapper.selectProductSkuBySale();

        return productSkuList;
    }
}
