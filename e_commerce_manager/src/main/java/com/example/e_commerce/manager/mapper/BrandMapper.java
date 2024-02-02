package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BrandMapper {
    //查询所有品牌列表
    List<Brand> selectByPage();

    //添加品牌
    void addBrand(Brand brand);

    //修改品牌
    void updateBrand(Brand brand);

    //逻辑删除品牌
    @Update("update brand set is_deleted = 1 where id = #{id}")
    void deletedById(Long id);
}
