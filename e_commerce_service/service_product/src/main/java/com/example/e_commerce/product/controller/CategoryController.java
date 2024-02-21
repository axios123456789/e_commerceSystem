package com.example.e_commerce.product.controller;

import com.example.e_commerce.model.entity.product.Category;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //查询所有分类，树形封装
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree(){
        List<Category> categoryList = categoryService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
