package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.manager.mapper.ProductSpecMapper;
import com.example.e_commerce.manager.service.ProductSpecService;
import com.example.e_commerce.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    @Autowired
    private ProductSpecMapper productSpecMapper;

    /**
     * 条件分页查询商品规格
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        //分页参数设置
        PageHelper.startPage(page, limit);

        //查询所有商品规格数据
        List<ProductSpec> productSpecs = productSpecMapper.selectAll();

        //开始分页
        PageInfo<ProductSpec> pageInfo = new PageInfo<>(productSpecs);

        return pageInfo;
    }

    /**
     * 添加商品规格
     * @param productSpec
     * @return
     */
    @Override
    public boolean save(ProductSpec productSpec) {
        try {
            productSpecMapper.add(productSpec);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 修改商品规格
     * @param productSpec
     * @return
     */
    @Override
    public boolean update(ProductSpec productSpec) {
        try {
            productSpecMapper.update(productSpec);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 删除商品规格
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Long id) {
        try {
            productSpecMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 查询所有规格
     * @return
     */
    @Override
    public List<ProductSpec> findAll() {
        List<ProductSpec> productSpecs = productSpecMapper.selectAll();
        return productSpecs;
    }
}
