package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.service.BrandService;
import com.example.e_commerce.model.entity.product.Brand;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    //品牌列表
    @GetMapping("/{page}/{limit}")
    public Result brandList(@PathVariable("page") Integer page,
                            @PathVariable("limit") Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加品牌
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand){
        boolean flag = brandService.save(brand);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "添加品牌失败，请检查添加信息无误后重试~");
        }
    }

    //品牌修改
    @PutMapping("/update")
    public Result update(@RequestBody Brand brand){
        boolean flag = brandService.update(brand);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "品牌修改失败，请检查修改信息无误后重试~");
        }
    }

    //删除品牌
    @DeleteMapping("/deleted/{id}")
    public Result deleted(@PathVariable("id") Long id){
        boolean flag = brandService.deleted(id);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "删除失败，请重试！");
        }
    }
}
