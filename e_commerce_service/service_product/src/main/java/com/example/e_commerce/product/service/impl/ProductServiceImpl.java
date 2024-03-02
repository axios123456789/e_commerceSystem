package com.example.e_commerce.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.e_commerce.model.dto.h5.ProductSkuDto;
import com.example.e_commerce.model.entity.product.Product;
import com.example.e_commerce.model.entity.product.ProductDetails;
import com.example.e_commerce.model.entity.product.ProductSku;
import com.example.e_commerce.model.vo.h5.ProductItemVo;
import com.example.e_commerce.product.mapper.ProductDetailsMapper;
import com.example.e_commerce.product.mapper.ProductMapper;
import com.example.e_commerce.product.mapper.ProductSkuMapper;
import com.example.e_commerce.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    /**
     * 查询销量前十的sku商品
     * @return
     */
    @Override
    public List<ProductSku> findProductSkuBySale() {
        List<ProductSku> productSkuList = productSkuMapper.selectProductSkuBySale();

        return productSkuList;
    }

    /**
     * 分页查询sku商品
     * @param page
     * @param limit
     * @param productSkuDto
     * @return
     */
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        //设置分页参数
        PageHelper.startPage(page, limit);

        //条件查询所有商品sku
        List<ProductSku> productSkuList = productSkuMapper.selectByCondition(productSkuDto);

        //封装成分页数据
        PageInfo<ProductSku> pageInfo = new PageInfo<>(productSkuList);

        return pageInfo;
    }

    /**
     * 查看商品详细信息
     * @param skuId
     * @return
     */
    @Override
    public ProductItemVo item(Long skuId) {
        //1.根据skuId查询商品规格信息
        ProductSku productSku = productSkuMapper.getById(skuId);

        //2.获得product_id，根据其查到商品信息
        Long productId = productSku.getProductId();
        Product product = productMapper.getById(productId);

        //3.根据product_id查询商品详细信息
        ProductDetails productDetails = productDetailsMapper.getByProductId(productId);

        //4.封装map集合，商品规格对应skuId信息
        Map<String, Object> skuSpecValueMap = new HashMap<>();
        //根据product_id查询所有sku
        List<ProductSku> productSkuList = productSkuMapper.getByProductId(productId);
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });

        //5.创建封装数据对象
        ProductItemVo productItemVo = new ProductItemVo();

        //6.封装相应数据
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));

        return productItemVo;
    }

    /**
     * 根据skuId查询sku信息
     * @param skuId
     * @return
     */
    @Override
    public ProductSku getBySkuId(Long skuId) {
        ProductSku productSku = productSkuMapper.getById(skuId);
        return productSku;
    }
}
