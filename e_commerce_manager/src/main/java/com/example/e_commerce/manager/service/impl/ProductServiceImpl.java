package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.manager.mapper.ProductDetailsMapper;
import com.example.e_commerce.manager.mapper.ProductMapper;
import com.example.e_commerce.manager.mapper.ProductSkuMapper;
import com.example.e_commerce.manager.service.ProductService;
import com.example.e_commerce.model.dto.product.ProductDto;
import com.example.e_commerce.model.entity.product.Product;
import com.example.e_commerce.model.entity.product.ProductDetails;
import com.example.e_commerce.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    /**
     * 条件分页查询商品列表
     * @param page
     * @param limit
     * @param productDto
     * @return
     */
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        //分页参数
        PageHelper.startPage(page, limit);

        //条件查询所有
        List<Product> products = productMapper.findByCondition(productDto);

        //封装成分页对象
        PageInfo<Product> pageInfo = new PageInfo<>(products);

        return pageInfo;
    }

    /**
     * 商品添加
     * @param product
     * @return
     */
    @Override
    @Transactional
    public boolean save(Product product) {
        try {
            //1.保存基本信息到Product中
            productMapper.save(product);

            //2.获取sku列表集合，保存sku数据到product_sku中
            List<ProductSku> productSkuList = product.getProductSkuList();
            for (int i = 0; i < productSkuList.size(); i++) {
                ProductSku productSku = productSkuList.get(i);

                //商品编号
                productSku.setSkuCode(product.getId()+"_"+i);
                //商品ID
                productSku.setProductId(product.getId());
                //skuName
                productSku.setSkuName(product.getName()+productSku.getSkuSpec());
                productSku.setSaleNum(0);                               // 设置销量
                productSku.setStatus(0);
                productSkuMapper.save(productSku);
            }

            //3.保存商品详细数据到product_details中
            ProductDetails productDetails = new ProductDetails();
            productDetails.setProductId(product.getId());
            productDetails.setImageUrls(product.getDetailsImageUrls());
            productDetailsMapper.save(productDetails);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    @Override
    public Product getById(Long id) {
        //1.根据id查询商品基本信息
        Product product = productMapper.getProductById(id);

        //2.根据商品id查询sku信息
        List<ProductSku> productSkuList = productSkuMapper.getProductSkuListByProductId(id);
        product.setProductSkuList(productSkuList);

        //3.根据商品id查询商品详情
        ProductDetails productDetails = productDetailsMapper.getProductDetailsByProductId(id);
        String imageUrls = productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);

        return product;
    }

    /**
     * 商品修改
     * @param product
     * @return
     */
    @Override
    @Transactional
    public boolean update(Product product) {
        try {
            //修改商品基本信息
            productMapper.updateById(product);

            //修改商品sku信息即product_sku
            List<ProductSku> productSkuList = product.getProductSkuList();
            productSkuList.forEach(productSku -> {
                productSkuMapper.updateById(productSku);
            });

            //修改商品详情即product_details
            String detailsImageUrls = product.getDetailsImageUrls();
            ProductDetails productDetailsByProductId = productDetailsMapper.getProductDetailsByProductId(product.getId());
            productDetailsByProductId.setImageUrls(detailsImageUrls);
            productDetailsMapper.updateById(productDetailsByProductId);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteById(Long id) {
        try {
            //1.在product中删除
            productMapper.deleteById(id);

            //2.在product_sku中删除
            productSkuMapper.deleteByProductId(id);

            //3.在product_details中删除
            productDetailsMapper.deleteByProductId(id);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    /**
     * 审核
     * @param id
     * @param auditStatus
     * @return
     */
    @Override
    public boolean updateAuditStatus(Long id, Integer auditStatus) {
        try {
            Product product = new Product();
            product.setId(id);
            if (auditStatus == 1){
                product.setAuditStatus(1);
                product.setAuditMessage("审批通过");
            }else{
                product.setAuditStatus(-1);
                product.setAuditMessage("审批不通过");
            }
            productMapper.updateById(product);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 上下架
     * @param id
     * @param status
     * @return
     */
    @Override
    public boolean updateStatus(Long id, Integer status) {
        try {
            Product product = new Product();
            product.setId(id);
            product.setStatus(status);
            productMapper.updateById(product);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
