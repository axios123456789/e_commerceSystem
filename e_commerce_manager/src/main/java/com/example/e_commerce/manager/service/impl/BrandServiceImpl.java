package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.manager.mapper.BrandMapper;
import com.example.e_commerce.manager.service.BrandService;
import com.example.e_commerce.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 品牌列表
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        //设置分页参数
        PageHelper.startPage(page, limit);

        //查询所有品牌列表
        List<Brand> brands = brandMapper.selectByPage();

        //封装成分页对象
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        return pageInfo;
    }

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @Override
    public boolean save(Brand brand) {
        try {
            brandMapper.addBrand(brand);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 品牌修改
     * @param brand
     * @return
     */
    @Override
    public boolean update(Brand brand) {
        try {
            brandMapper.updateBrand(brand);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 逻辑删除品牌
     * @param id
     * @return
     */
    @Override
    public boolean deleted(Long id) {
        try {
            brandMapper.deletedById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 查询所以品牌
     * @return
     */
    @Override
    public List<Brand> findAll() {
        List<Brand> brands = brandMapper.selectByPage();

        return brands;
    }
}
