package com.example.e_commerce.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.e_commerce.model.entity.product.Category;
import com.example.e_commerce.product.mapper.CategoryMapper;
import com.example.e_commerce.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    /**
     * 查询所有分类，树形封装
     * @return
     */
    @Cacheable(value = "category",key="'all'")
    @Override
    public List<Category> findCategoryTree() {
        //1 查询所有分类 返回list集合
        List<Category> allCategoryList = categoryMapper.selectAll();

        //2 遍历所有分类list集合，通过条件 parentid=0得到所有一级分类
        List<Category> oneCategoryList =
                allCategoryList.stream()
                        .filter(item -> item.getParentId().longValue() == 0)
                        .collect(Collectors.toList());

        //3 遍历所有一级分类list集合，条件判断： id = parentid，得到一级下面二级分类
        oneCategoryList.forEach(oneCategory ->{
            List<Category> twoCategoryList =
                    allCategoryList.stream()
                            .filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue())
                            .collect(Collectors.toList());
            //把二级分类封装到一级分类里面
            oneCategory.setChildren(twoCategoryList);

            //4 遍历所有二级分类， 条件判断： id = parentid，得到二级下面三级分类
            twoCategoryList.forEach(twoCategory ->{
                List<Category> threeCategoryList =
                        allCategoryList.stream()
                                .filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue())
                                .collect(Collectors.toList());
                //把三级分类封装到二级分类里面
                twoCategory.setChildren(threeCategoryList);
            });
        });

        return oneCategoryList;
    }
}
