package com.example.e_commerce.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.e_commerce.common_service.exception.MyException;
import com.example.e_commerce.manager.listen.ExcelListener;
import com.example.e_commerce.manager.mapper.CategoryMapper;
import com.example.e_commerce.manager.service.CategoryService;
import com.example.e_commerce.model.entity.product.Category;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分类查询，每次只查一层数据
     *
     * @param id
     * @return
     */
    @Override
    public List<Category> findCategoryList(Long id) {
        //根据id条件查询，返回list集合
        List<Category> categoryList = categoryMapper.selectCategoryListByParentId(id);

        //判断每个分类是否有下一层分类，如果有就设置hasChildren = true
        if (!CollectionUtils.isEmpty(categoryList)) {
            categoryList.forEach(category -> {
                //判断每个分类是否有下一层分类
                int count = categoryMapper.selectCategoryCountByParentId(category.getId());

                if (count > 0) {
                    category.setHasChildren(true);
                } else {
                    category.setHasChildren(false);
                }
            });
        }

        return categoryList;
    }

    /**
     * 导出
     *
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //设置响应头信息和其他信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("分类数据", "UTF-8");

            //设置响应头信息
            response.setHeader("Content-disposition", "attachment;filename="+fileName+".xlsx");

            //查询所有分类，返回list集合
            List<Category> categoryList = categoryMapper.selectAllCategoryLists();

            //将查到的所有分类转换成对应的vo对象
            List<CategoryExcelVo> categoryExcelVos = new ArrayList<>();
            for (Category category:categoryList){
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo);
                categoryExcelVos.add(categoryExcelVo);
            }

            //调用EasyExcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     * 导入
     * @param file
     */
    @Override
    public void importData(MultipartFile file) {
        //监听器
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener)
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
    }
}