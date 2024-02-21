package com.example.e_commerce.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.e_commerce.model.entity.product.Category;
import com.example.e_commerce.product.mapper.CategoryMapper;
import com.example.e_commerce.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有一级分类
     *   这里由于我们查的一级分类算是热点数据，所以我们要用到redis数据库作为缓存层来缓存数据
     * @return
     */
    @Override
    public List<Category> findOneCategory() {
        //1.查询redis数据库，看是否包含了所有一级分类数据
        String categoryOneJson = (String) redisTemplate.opsForValue().get("category:one");

        //2.如果包含了所有一级分类数据，则将数据直接返回
        if (StringUtils.hasText(categoryOneJson)){
            //将categoryOneJson转换为List<Category>
            List<Category> existCategoryList = JSON.parseArray(categoryOneJson, Category.class);
            return existCategoryList;
        }

        //3.如果没有一级分类数据，则查询mysql数据库拿到对应数据，然后存到redis数据库中再返回
        List<Category> categoryList = categoryMapper.selectOneCategory();
        redisTemplate.opsForValue().set("category:one",
                                             JSON.toJSONString(categoryList),
                                             7, TimeUnit.DAYS);

        return categoryList;
    }
}
