package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);   //分类查询，每次只查一层数据

    void exportData(HttpServletResponse response);  //导出

    void importData(MultipartFile file);    //导入
}
