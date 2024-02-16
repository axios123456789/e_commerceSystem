package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.service.ProductUnitService;
import com.example.e_commerce.model.entity.base.ProductUnit;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/product/productUnit")
public class ProductUnitController {
    @Autowired
    private ProductUnitService productUnitService;

    //查询所有
    @GetMapping("/findAll")
    public Result findAll(){
        List<ProductUnit> productUnits = productUnitService.findAll();
        return Result.build(productUnits, ResultCodeEnum.SUCCESS);
    }
}
