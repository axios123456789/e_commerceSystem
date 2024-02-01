package com.example.e_commerce.manager.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.example.e_commerce.manager.mapper.CategoryMapper;
import com.example.e_commerce.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

//excel表格的监听器
public class ExcelListener<T> implements ReadListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    //构造传递mapper，操作数据库，不能交给spring管理，因为并发的情况下spring是单例的，不能区分操作的excel表格
    private CategoryMapper categoryMapper;
    public ExcelListener(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    //从第二行开始读取，把每行读取内容封装到t对象中
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        //把每行数据对象t放到cachedDataList集合里面
        cachedDataList.add(t);
        //达到BATCH_COUNT了，需要去存储一次数据库，防止几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT){
            //调用方法一次性批量添加数据到数据库里面
            saveData();

            //清理list集合
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    //所有操作完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //保存数据
        saveData();
    }

    //批量保存数据到数据库的方法
    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>)cachedDataList);
    }
}
