<template>
  <div class="tools-div">
     <el-button type="success" size="small" @click="addBrand">添加</el-button>
  </div>

  <el-dialog v-model="dialogVisible" title="添加或修改" width="30%">
    <el-form label-width="120px">
      <el-form-item label="品牌名称">
        <el-input v-model="brand.name"/>
      </el-form-item>
      <el-form-item label="品牌图标">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8501/admin/system/fileUpload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :headers="headers"
        >
          <img v-if="brand.logo" :src="brand.logo" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">提交</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-table
      :data="list"
      style="width: 100%">
    <el-table-column prop="name" label="品牌名称" />
    <el-table-column prop="logo" label="品牌图标" #default="scope">
        <img :src="scope.row.logo" width="50" />
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" />
    <el-table-column label="操作" align="center" width="200" #default="scope">
      <el-button type="primary" size="small" @click="editBrand(scope.row)">
        修改
      </el-button>
      <el-button type="danger" size="small" @click="deleteBrandById(scope.row)">
        删除
      </el-button>
    </el-table-column>
  </el-table>

  <!--分页条-->
  <el-pagination
      v-model:current-page="pageParams.page"
      v-model:page-size="pageParams.limit"
      :page-sizes="[10, 20, 50, 100]"
      @size-change="fetchData"
      @current-change="fetchData"
      layout="total, sizes, prev, pager, next"
      :total="total"
  />

</template>

<script setup>
import {onMounted, ref} from "vue";
import {AddBrand, BrandList, DeletedBrandById, UpdateBrand} from "@/api/brand";
import {ElMessage, ElMessageBox} from "element-plus";
import {useApp} from "@/pinia/modules/app";

//------------添加或修改品牌---------
const headers = {
  // 从pinia中获取token，在进行文件上传的时候将token设置到请求头中
  token: useApp().authorization.token
}
// 定义提交表单数据模型
const defaultForm = {
  id: '',
  name: '',
  logo: ""
}
const brand = ref(defaultForm)
const dialogVisible = ref(false)

//上传
const handleAvatarSuccess = (response) => {
  brand.value.logo = response.data
}

//添加品牌
const addBrand = () => {
  brand.value = {};
  dialogVisible.value = true;
}

//修改品牌
const editBrand = (row) => {
  brand.value = {...row};
  dialogVisible.value = true;
}

// 保存数据
const saveOrUpdate = () => {
  if (!brand.value.id) {
    saveData()
  }else {
    updateData()
  }
}

//添加数据
const saveData = async () => {
  const {code, message} = await AddBrand(brand.value);
  if (code === 200){
    dialogVisible.value = false;
    ElMessage.success(message);
    fetchData();
  }else {
    ElMessage.error(message);
  }
}

//修改数据
const updateData = async () => {
  const {code, message} = await UpdateBrand(brand.value);
  if (code === 200){
    dialogVisible.value = false;
    ElMessage.success(message);
    fetchData();
  }else {
    ElMessage.error(message);
  }
}

//------------删除品牌--------------
const deleteBrandById = (row) => {
  ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', 'Warning', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    const {code, message} = await DeletedBrandById(row.id);
    if (code === 200){
      ElMessage.success(message);
      fetchData();
    }else {
      ElMessage.error(message);
    }
  })
}

//------------品牌列表--------------
//定义表格数据模型
let list = ref([]);

//总页数
let total = ref(0);

//分页数据
const pageParamsForm = {
  page: 1,  //当前页
  limit: 3,  //每页记录数
};
const pageParams = ref(pageParamsForm);

//钩子函数
onMounted(() => {
  fetchData();
})

//列表方法
const fetchData = async () => {
  const {data} = await BrandList(pageParams.value.page, pageParams.value.limit);
  //console.log("data",data)
  list.value = data.list;
  total.value = data.total;
}

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





<!--<template>
  <div class="tools-div">
    <el-button type="success" size="small" @click="addShow">添 加</el-button>
  </div>

  <el-dialog v-model="dialogVisible" title="添加或修改" width="30%">
    <el-form label-width="120px">
      <el-form-item label="品牌名称">
        <el-input v-model="brand.name"/>
      </el-form-item>
      <el-form-item label="品牌图标">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8501/admin/system/fileUpload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :headers="headers"
        >
          <img v-if="brand.logo" :src="brand.logo" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">提交</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-table :data="list" style="width: 100%">
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

  <el-pagination
      v-model:current-page="pageParams.page"
      v-model:page-size="pageParams.limit"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
  />

</template>

<script setup>
import { ref , onMounted } from 'vue'
import {AddBrand, BrandList} from '@/api/brand.js'
import { ElMessage} from 'element-plus'
import { useApp } from '@/pinia/modules/app'
///////////////////////////////添加
const headers = {
  // 从pinia中获取token，在进行文件上传的时候将token设置到请求头中
  token: useApp().authorization.token
}
// 定义提交表单数据模型
const defaultForm = {
  id: '',
  name: '',
  logo: ""
}
const brand = ref(defaultForm)
const dialogVisible = ref(false)

// 显示添加品牌表单
const addShow = () => {
  brand.value = {}
  dialogVisible.value = true
}

//上传
const handleAvatarSuccess = (response) => {
  brand.value.logo = response.data
}

// 保存数据
const saveOrUpdate = () => {
  if (!brand.value.id) {
    saveData()
  }
}

// 新增
const saveData = async () => {
  await AddBrand(brand.value)
  dialogVisible.value = false
  ElMessage.success('操作成功')
  fetchData()
}
///////////////////////////////
// 定义表格数据模型
const list = ref([])

// 分页条数据模型
const total = ref(0)

//分页条数据模型
const pageParamsForm = {
  page: 1, // 页码
  limit: 10, // 每页记录数
}
const pageParams = ref(pageParamsForm)

// 钩子函数
onMounted(()=> {
  fetchData()
})

//页面变化
const handleSizeChange = size => {
  pageParams.value.limit = size
  fetchData()
}
const handleCurrentChange = number => {
  pageParams.value.page = number
  fetchData()
}

// 分页查询
const fetchData = async () => {
  const {code , message , data} = await BrandList(pageParams.value.page , pageParams.value.limit)
  list.value = data.list
  total.value = data.total
}

</script>

<style scoped>
.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
</style>-->
