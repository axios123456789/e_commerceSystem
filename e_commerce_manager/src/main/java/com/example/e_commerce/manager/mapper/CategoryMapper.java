package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.product.Category;
import com.example.e_commerce.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //分类查询，每次只查一层数据
    List<Category> selectCategoryListByParentId(Long id);

    //查询下一层分类的数量
    @Select("select count(*) from category where parent_id = #{id} and is_deleted = 0")
    int selectCategoryCountByParentId(Long id);

    //查询所有分类
    List<Category> selectAllCategoryLists();

    //批量保存数据
    void batchInsert(List<CategoryExcelVo> cachedDataList);
}
