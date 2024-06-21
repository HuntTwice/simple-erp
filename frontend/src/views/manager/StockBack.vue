<template>
  <div>
    <div class="search">
      <el-input v-model="selectConditions.goodsName" placeholder="商品名称"
                style="width: 200px;margin-right: 10px"></el-input>
      <el-select v-model="selectConditions.supplierId" placeholder="供应商" style="margin-right: 10px">
        <el-option
            v-for="item in suppliers"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="warning" plain @click="handleAdd">退货</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" style="width: 100%" height="500" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="goodsName" label="商品名称"></el-table-column>
        <el-table-column prop="supplierName" label="供应商"></el-table-column>
        <el-table-column prop="payType" label="支付类型"></el-table-column>
        <el-table-column prop="time" label="退货时间"></el-table-column>
        <el-table-column prop="operator" label="操作人"></el-table-column>
        <el-table-column prop="num" label="退货数量"></el-table-column>
        <el-table-column prop="unit" label="商品规格"></el-table-column>
        <el-table-column prop="price" label="退货价格"></el-table-column>
        <el-table-column prop="totalPrice" label="退货总价格"></el-table-column>
        <el-table-column prop="comment" label="备注"></el-table-column>

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


  </div>
</template>

<script>
import {addStockBackService, stockBackList} from '@/api/stockBack'
import {selectAllSupplier} from '@/api/supplier'



export default {
  name: "stockBack",
  data() {
    return {
      tableData: [],  // 所有的数据
      suppliers: [], //供应商的信息
      goods: [], //商品信息
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0,
      selectConditions: {
        supplierId: null,
        goodsName: ''
      },
      statusOptions: [{value: true, label: '启用'},
        {value: false, label: '禁用'}],
      formVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [{required: true, message: '请输入商品名称', trigger: 'blur'}],
        stockBackId: [{required: true, message: '请选择供应商', trigger: 'blur'}],
        num: [
          {required: true, message: '不能为空'},
          {type: 'number', message: '必须为数字值'}
        ]
      },
      ids: []
    }
  },
  created() {
    this.load(this.pageNum, this.pageSize)
    this.loadSupplier()
    // this.loadGoods()
  },
  methods: {



    //加载供应商信息
    async loadSupplier() {
      let result = await selectAllSupplier()
      this.suppliers = result.data
    },

    //加载表格数据
    async load() {
      let result = await stockBackList(this.selectConditions, this.pageNum, this.pageSize)
      this.tableData = result.data.records
      this.total = result.data.total
    },

    //保存
    save() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          try {
            if (this.form.id == null)
              await addStockBackService(this.form)
            else
              await updateStockBackService(this.form)
            this.$message.success('保存成功')
            this.formVisible = false
            await this.load()
          } catch (e) {
          }
        }
      })
    },

    //删除
    del(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"})
          .then(async () => {
                try {
                  await deleteStockBackService(id)
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
        let result = await deleteStockBackBatchService(this.ids)
        this.$message.success("批量删除成功")
        this.load()
      }).catch(() => {
      })
    },


    //点击重置按钮时触发
    reset() {
      this.selectConditions = {}
      this.load()
    },

    // 当前选中的所有的行数据
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
    },

    // 新增数据
    handleAdd() {
      this.form = {}  // 新增数据的时候清空数据
      this.formVisible = true   // 打开弹窗
    },

    // 编辑数据
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.formVisible = true   // 打开弹窗
    },

    //当页码变化时触发
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },

    //当更改分页尺寸时触发
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load()
    }

  }
}
</script>

<style scoped>

</style>
