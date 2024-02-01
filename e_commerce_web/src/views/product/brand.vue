<template>
  <div class="tools-div">
     <el-button type="success" size="small">添加</el-button>
  </div>

  <el-table
      :data="list"
      style="width: 100%">
    <el-table-column prop="name" label="品牌名称" />
    <el-table-column prop="logo" label="品牌图标" #default="scope">
        <img :src="scope.row.logo" width="50" />
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" />
    <el-table-column label="操作" align="center" width="200" >
      <el-button type="primary" size="small">
        修改
      </el-button>
      <el-button type="danger" size="small">
        删除
      </el-button>
    </el-table-column>
  </el-table>



</template>

<script>
import {ref} from "vue";

//------------品牌列表--------------
//定义表格数据模型
const list = ref([]);

</script>

<style scoped>
  .tools-div {
    margin: 10px 0;
    padding: 10px;
    border: 1px solid #ebeef5;
    border-radius: 3px;
    background-color: #fff;
  }
</style>