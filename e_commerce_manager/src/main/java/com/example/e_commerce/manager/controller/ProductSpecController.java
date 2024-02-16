package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.service.ProductSpecService;
import com.example.e_commerce.model.entity.product.ProductSpec;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productSpec")
public class ProductSpecController {
    @Autowired
    private ProductSpecService productSpecService;

    //查询所有规格
    @GetMapping("/findAll")
    public Result findAll(){
        List<ProductSpec> productSpecs = productSpecService.findAll();
        return Result.build(productSpecs, ResultCodeEnum.SUCCESS);
    }

    //商品规格条件分页查询
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable("page") Integer page,
                             @PathVariable("limit") Integer limit){
        PageInfo<ProductSpec> pageInfo = productSpecService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @PostMapping("/save")
    public Result save(@RequestBody ProductSpec productSpec){
        boolean flag = productSpecService.save(productSpec);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "添加该商品规格失败，请检查添加信息无误后重试~");
        }
    }

    //修改
    @PutMapping("/update")
    public Result update(@RequestBody ProductSpec productSpec){
        boolean flag = productSpecService.update(productSpec);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "修改该商品规格失败，请检查修改信息无误后重试~");
        }
    }

    //删除
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Long id){
        boolean flag = productSpecService.deleteById(id);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "删除商品规格失败，请重试~");
        }
    }
}
