package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.service.CategoryBrandService;
import com.example.e_commerce.model.dto.product.CategoryBrandDto;
import com.example.e_commerce.model.entity.product.Brand;
import com.example.e_commerce.model.entity.product.CategoryBrand;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/categoryBrand")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;

    //根据分类id查询对应品牌数据
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable("categoryId") Long categoryId){
        List<Brand> brands = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brands, ResultCodeEnum.SUCCESS);
    }

    //分类品牌条件分页查询
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable("page") Integer page,
                             @PathVariable("limit") Integer limit,
                             CategoryBrandDto categoryBrandDto){
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page, limit, categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand){
        boolean flag = categoryBrandService.save(categoryBrand);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "添加分类品牌失败~");
        }
    }

    //修改
    @PutMapping("/update")
    public Result update(@RequestBody CategoryBrand categoryBrand){
        boolean flag = categoryBrandService.update(categoryBrand);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "修改分类品牌失败~");
        }
    }

    //删除
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        boolean flag = categoryBrandService.deleteById(id);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "删除分类品牌失败~");
        }
    }
}
