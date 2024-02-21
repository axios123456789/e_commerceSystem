package com.example.e_commerce.product.controller;

import com.example.e_commerce.model.entity.product.Category;
import com.example.e_commerce.model.entity.product.ProductSku;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.h5.IndexVo;
import com.example.e_commerce.product.service.CategoryService;
import com.example.e_commerce.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "首页接口管理")
@RestController
@RequestMapping("/api/product/index")
@CrossOrigin
public class IndexController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result index(){
        //查询所有一级分类
        List<Category> categoryList = categoryService.findOneCategory();

        //查询销量前十的商品sku
        List<ProductSku> productSkuList = productService.findProductSkuBySale();

        //封装数据到VO对象里面去
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);

        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }
}
