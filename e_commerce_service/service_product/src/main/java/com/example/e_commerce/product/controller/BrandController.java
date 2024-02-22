package com.example.e_commerce.product.controller;

import com.example.e_commerce.model.entity.product.Brand;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @Operation(summary = "获得全部品牌")
    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> brands = brandService.findAll();
        return Result.build(brands, ResultCodeEnum.SUCCESS);
    }
}
