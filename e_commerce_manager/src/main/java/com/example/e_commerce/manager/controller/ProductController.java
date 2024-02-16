package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.service.ProductService;
import com.example.e_commerce.model.dto.product.ProductDto;
import com.example.e_commerce.model.entity.product.Product;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //商品列表
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable("page") Integer page,
                             @PathVariable("limit") Integer limit,
                             ProductDto productDto){
        PageInfo<Product> pageInfo = productService.findByPage(page, limit, productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加
    @PostMapping("/save")
    public Result save(@RequestBody Product product){
        boolean flag = productService.save(product);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "添加商品失败，请检查添加信息无误后重试~");
        }
    }

    //根据商品id查询商品信息
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable("id") Long id){
        Product product = productService.getById(id);
        return Result.build(product, ResultCodeEnum.SUCCESS);
    }

    //保存修改的数据
    @PutMapping("/updateById")
    public Result updateById(@RequestBody Product product){
        boolean flag = productService.update(product);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "修改商品失败，请检查修改信息无误后重试~");
        }
    }

    //删除商品
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@Parameter(name = "id", description = "商品id", required = true) @PathVariable Long id){
        boolean flag = productService.deleteById(id);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "删除商品失败，请重试~");
        }
    }

    //审核
    @GetMapping("/updateAuditStatus/{id}/{auditStatus}")
    public Result audit(@PathVariable("id") Long id,
                        @PathVariable("auditStatus") Integer auditStatus){
        boolean flag = productService.updateAuditStatus(id, auditStatus);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "审核出错~");
        }
    }

    //上下架
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") Long id,
                               @PathVariable("status") Integer status){
        boolean flag = productService.updateStatus(id, status);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "上下架出错~");
        }
    }
}
