package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductUnitMapper {
    //查询所有
    List<ProductUnit> findAll();
}
