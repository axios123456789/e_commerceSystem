<template>
  <div class="search-div">
    <el-form label-width="70px" size="small">
      <el-row>
        <el-col :span="12">
          <el-form-item label="品牌">
            <el-select
                class="m-2"
                placeholder="选择品牌"
                size="small"
                style="width: 100%"
                v-model="queryDto.brandId"
            >
              <el-option
                  v-for="item in brandList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="分类">
            <el-cascader
                :props="categoryProps"
                style="width: 100%"
                v-model="searchCategoryIdList"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row style="display:flex">
        <el-button type="primary" size="small" @click="fetchData()">
          搜索
        </el-button>
        <el-button size="small" @click="resetData">重置</el-button>
      </el-row>
    </el-form>
  </div>

  <div class="tools-div">
    <el-button type="success" size="small" @click="addShow">添 加</el-button>
  </div>

  <el-dialog v-model="dialogVisible" title="添加或修改" width="30%">
    <el-form label-width="120px">
      <el-form-item label="品牌">
        <el-select
            class="m-2"
            placeholder="选择品牌"
            size="small"
            v-model="categoryBrand.brandId"
        >
          <el-option
              v-for="item in brandList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="分类">
        <el-cascader
            :props="categoryProps"
            v-model="categoryBrand.categoryId"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveOrUpdate">提交</el-button>
        <el-button @click="dialogVisible = false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <el-table :data="list" style="width: 100%">
    <el-table-column prop="categoryName" label="分类" />
    <el-table-column prop="brandName" label="品牌" />
    <el-table-column prop="logo" label="品牌图标" #default="scope">
      <img :src="scope.row.logo" width="50" />
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" />
    <el-table-column label="操作" align="center" width="200" #default="scope">
      <el-button type="primary" size="small" @click="editShow(scope.row)">
        修改
      </el-button>
      <el-button type="danger" size="small" @click="deleteShow(scope.row)">
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
import {FindAllBrand} from '@/api/brand.js'
import { FindCategoryByParentId } from '@/api/category.js'
import {
  DeleteCategoryBrandById,
  EditCategoryBrand,
  GetCategoryBrandPageList,
  SaveCategoryBrand
} from '@/api/categoryBrand.js'
import {ElMessage, ElMessageBox} from "element-plus";

//----------------------添加和修改----------------------------
const defaultForm = {//表单数据
  id: "",
  brandId: "",
  categoryId: "",
}
const categoryBrand = ref(defaultForm)

const dialogVisible = ref(false)

//点击添加按钮后触发
const addShow = () => {
  categoryBrand.value = {};
  dialogVisible.value = true;
}

//点击修改按钮后触发
const editShow = (row) => {
  categoryBrand.value = {...row};
  dialogVisible.value = true;
}

//提交
const saveOrUpdate = () => {
  //console.log("data", categoryBrand.value)
  if (categoryBrand.value.brandId == '' || categoryBrand.value.brandId == null) {
    ElMessage.info('品牌信息必须选择')
    return
  }
  //categoryId为数组：[1,2,3]
  if (categoryBrand.value.categoryId == null || categoryBrand.value.categoryId.length != 3) {
    ElMessage.info('分类信息必须选择')
    return
  }
  //系统只需要三级分类id
  categoryBrand.value.categoryId = categoryBrand.value.categoryId[2]

  if (!categoryBrand.value.id){
    //添加
    saveData();
  }else {
    //修改
    editData();
  }
}

//调用后端接口，进行添加操作
const saveData = async () => {
  const {code, message} = await SaveCategoryBrand(categoryBrand.value);
  if (code === 200){
    dialogVisible.value = false;
    ElMessage.success(message);
    fetchData();
  }else {
    ElMessage.error(message);
  }
}

//调用修改接口
const editData = async () => {
  const {code, message} = await EditCategoryBrand(categoryBrand.value);

  if (code === 200){
    dialogVisible.value = false;
    ElMessage.success(message);
    fetchData();
  }else {
    ElMessage.error(message);
  }
}

//----------------------删除分类品牌-------------------------
const deleteShow = (row) => {
  ElMessageBox.confirm('此操作将永久删除该记录, 是否继续?', 'Warning', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    const {code, message} = await DeleteCategoryBrandById(row.id);
    if (code === 200){
      ElMessage.success(message);
      fetchData();
    }else {
      ElMessage.error(message);
    }
  })
}

//----------------------条件分页查询--------------------------
const props = {
  lazy: true,
  value: 'id',
  label: 'name',
  leaf: 'leaf',
  async lazyLoad(node, resolve) {   // 加载数据的方法
    if (typeof node.value == 'undefined') node.value = 0
    const { data } = await FindCategoryByParentId(node.value)
    data.forEach(function(item) {       //hasChildren判断是否有子节点
      item.leaf = !item.hasChildren
    })
    resolve(data)  // 返回数据
  }
};

const categoryProps = ref(props)

//查询所有品牌
const selectAllBrandList = async () => {
  const { data } = await FindAllBrand()
  brandList.value = data
}

// 定义搜索表单数据模型
const brandList = ref([])
// 定义表格数据模型
const list = ref([])
// 分页条数据模型
const total = ref(0)
// 搜索表单数据模型
const queryDto = ref({ brandId: '', categoryId: '' })
const searchCategoryIdList = ref([])
//分页条数据模型
const pageParamsForm = {
  page: 1,   // 页码
  limit: 2, // 每页记录数
}
const pageParams = ref(pageParamsForm)

// onMounted钩子函数
onMounted(() => {
  selectAllBrandList() // 查询所有的品牌数据
  fetchData()
})

//重置
const resetData = () => {
  queryDto.value = { brandId: '', categoryId: '' }
  searchCategoryIdList.value = []
  fetchData()
}

//分页变化
const handleSizeChange = size => {
  pageParams.value.limit = size
  fetchData()
}
const handleCurrentChange = number => {
  pageParams.value.page = number
  fetchData()
}

// 分页列表查询
const fetchData = async () => {
  if (searchCategoryIdList.value.length == 3) {
    queryDto.value.categoryId = searchCategoryIdList.value[2]
  }
  const { data } = await GetCategoryBrandPageList( pageParams.value.page, pageParams.value.limit, queryDto.value)
  list.value = data.list
  total.value = data.total
}
</script>

<style scoped>
.search-div {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
</style>
