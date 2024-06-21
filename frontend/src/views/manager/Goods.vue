<template>
  <div>
    <div class="search">
      <el-input v-model="selectConditions.name" placeholder="商品名称" style="width: 200px;margin-right: 10px"></el-input>
      <el-select v-model="selectConditions.supplierId" placeholder="供应商" style="margin-right: 10px">
        <el-option
            v-for="item in suppliers"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="selectConditions.status" placeholder="库存状态" style="margin-right: 10px">
        <el-option
            v-for="item in [{value:true,label:'可用'},{value:false,label:'不可用'}]"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增商品</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
      <el-upload style="display: inline-block;margin:auto 10px" :action="$baseUrl+'/goods/import'"
                 :show-file-list="false"
                 :headers="{Authorization:user.token}" :on-success="handleImport">
        <el-button type="primary" plain>批量导入</el-button>
      </el-upload>
      <el-button type="warning" plain @click="expBatch">批量导出</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" style="width: 100%" height="500" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="name" label="商品名称"></el-table-column>
        <el-table-column prop="supplierName" label="供应商"></el-table-column>
        <el-table-column prop="producer" label="商品产地"></el-table-column>
        <el-table-column prop="description" label="商品描述"></el-table-column>
        <el-table-column prop="num" label="库存"></el-table-column>
        <el-table-column prop="unit" label="规格"></el-table-column>
        <el-table-column prop="img" label="商品图片">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px;" v-if="scope.row.img"
                        :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="productNo" label="生产批号"></el-table-column>
        <el-table-column prop="approveNo" label="批准文号"></el-table-column>
        <el-table-column prop="status" label="状态">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status">可用</el-tag>
            <el-tag type="danger" v-else>不可用</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page=pageNum
            :page-sizes="[5,10,20]"
            :page-size=pageSize
            layout="total, sizes, prev, pager, next, jumper"
            :total=total>
        </el-pagination>
      </div>
    </div>

    <el-dialog :title="dialogTitle" :visible.sync="formVisible" width="40%" :close-on-click-modal="false"
               destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="商品名称" prop="name" >
          <el-input v-model="form.name" placeholder="商品名称" :disabled="form.id"></el-input>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select style="width: 100%" v-model="form.supplierId">
            <el-option v-for="item in suppliers" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="商品产地" prop="producer">
          <el-input v-model="form.producer" placeholder="商品产地"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="description">
          <el-input v-model="form.description" placeholder="商品描述"></el-input>
        </el-form-item>

        <el-form-item label="商品规格" prop="unit" >
          <el-input v-model="form.unit" placeholder="商品规格" :disabled="form.id"></el-input>
        </el-form-item>

        <el-form-item label="商品图片" prop="img">
          <el-upload
              :action="$baseUrl + '/upload'"
              :headers="{ 'Authorization': user.token }"
              :on-success="handleImgSuccess"
          >
            <el-button type="primary">上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="生产批号" prop="productNo">
          <el-input v-model="form.productNo" placeholder="生产批号"></el-input>
        </el-form-item>
        <el-form-item label="批准文号" prop="approveNo">
          <el-input v-model="form.approveNo" placeholder="批准文号"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="status">
          <el-switch
              v-model="form.status"

              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="可用"
              inactive-text="不可用">
          </el-switch>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  addGoodsService,
  deleteGoodsBatchService,
  deleteGoodsService,
  updateGoodsService,
  goodsList,
  exportBatch
} from '@/api/goods'
import {selectAllSupplier} from '@/api/supplier'


export default {
  name: "goods",
  data() {
    return {
      tableData: [],  // 所有的数据
      suppliers: [], //供应商的信息
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0,
      dialogTitle: '',//弹出框标题
      selectConditions: {
        supplierId: null,
        name: '',
        status: null,
      },
      statusOptions: [{value: true, label: '启用'},
        {value: false, label: '禁用'}],
      formVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [{required: true, message: '请输入商品名称', trigger: 'blur'}],
        supplierId: [{required: true, message: '请选择供应商', trigger: 'blur'}],
        unit: [{required: true, message: '请输入商品规格', trigger: 'blur'}],
        productNo: [{required: true, message: '请输入生产批号', trigger: 'blur'}],
        approveNo: [{required: true, message: '请输入批准文号', trigger: 'blur'}],
      },
      ids: []
    }
  },
  created() {
    this.load(this.pageNum, this.pageSize)
    this.loadSupplier()
  },
  methods: {
    //批量导出
    async expBatch() {
      try {
        let result = await exportBatch()

        let blob = result.data

        const url = window.URL.createObjectURL(new Blob([blob]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', '供应商信息表.xlsx');
        document.body.appendChild(link);
        link.click();
        link.parentNode.removeChild(link);
      } catch (error) {
        console.error('Error exporting file:', error);
      }

    },

    async loadSupplier() {
      let result = await selectAllSupplier()
      this.suppliers = result.data
    },
    async load() {
      let result = await goodsList(this.selectConditions, this.pageNum, this.pageSize)
      this.tableData = result.data.records

      this.total = result.data.total
    },

    save() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          try {
            if (this.form.id == null)
              await addGoodsService(this.form)
            else
              await updateGoodsService(this.form)
            this.$message.success('保存成功')
            this.formVisible = false
            await this.load()
          } catch (e) {
          }
        }
      })
    },

    del(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"})
          .then(async () => {
                try {
                  await deleteGoodsService(id)
                  this.$message.success('删除成功')
                  this.load()
                } catch (e) {
                }
              }
          )
          .catch(() =>
              this.$message({
                type: 'info',
                message: '已取消删除'
              }))
    },

    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(async () => {
        let result = await deleteGoodsBatchService(this.ids)
        this.$message.success("批量删除成功")
        this.load()
      }).catch(() => {
      })
    },


    reset() {
      this.selectConditions = {}
      this.load()
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },
    handleAdd() {   // 新增数据
      this.form = {status: true}; // 新增数据时初始化状态
      this.formVisible = true   // 打开弹窗
      this.dialogTitle = "新增数据"
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.formVisible = true   // 打开弹窗
      this.dialogTitle = "编辑"
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load()
    },
    handleImport(res) {
      if (res.code == '200') {
        this.$message.success("导入成功")
        this.load()
      } else {
        this.$message.error("导入失败")
      }
    },
    handleImgSuccess(response, file, fileList) {
      // 把头像属性换成上传的图片的链接
      this.form.img = response.data
    }
  }
}
</script>

<style scoped>

</style>
