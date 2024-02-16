package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.manager.mapper.CategoryBrandMapper;
import com.example.e_commerce.manager.service.CategoryBrandService;
import com.example.e_commerce.model.dto.product.CategoryBrandDto;
import com.example.e_commerce.model.entity.product.Brand;
import com.example.e_commerce.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    /**
     * 条件分页查询
     * @param page
     * @param limit
     * @param categoryBrandDto
     * @return
     */
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        //设置分页参数
        PageHelper.startPage(page, limit);

        //条件查询所有分类品牌
        List<CategoryBrand> categoryBrands = categoryBrandMapper.selectAllByCondition(categoryBrandDto);

        //将数据封装成pageInfo
        PageInfo<CategoryBrand> pageInfo = new PageInfo<>(categoryBrands);

        return pageInfo;
    }

    /**
     * 添加
     * @param categoryBrand
     * @return
     */
    @Override
    public boolean save(CategoryBrand categoryBrand) {
        try {
            categoryBrandMapper.add(categoryBrand);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 修改
     * @param categoryBrand
     * @return
     */
    @Override
    public boolean update(CategoryBrand categoryBrand) {
        try {
            categoryBrandMapper.update(categoryBrand);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Long id) {
        try {
            categoryBrandMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 根据分类id查询对应品牌数据
     * @param categoryId
     * @return
     */
    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.selectBrandByCategoryId(categoryId);
    }
}
