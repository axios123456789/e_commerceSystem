package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.dto.product.CategoryBrandDto;
import com.example.e_commerce.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    //条件分页查询
    List<CategoryBrand> selectAllByCondition(CategoryBrandDto categoryBrandDto);

    //添加
    void add(CategoryBrand categoryBrand);

    //修改
    void update(CategoryBrand categoryBrand);

    //删除
    @Update("update category_brand set is_deleted = 1 where id = #{id}")
    void deleteById(Long id);
}
