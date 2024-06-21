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
      <el-input v-model="selectConditions.operator" placeholder="操作人"
                style="width: 200px;margin-right: 10px"></el-input>
      <el-input v-model="selectConditions.time" placeholder="时间"
                style="width: 200px;margin-right: 10px"></el-input>

      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">商品进货</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" style="width: 100%" height="500" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="goodsName" label="商品名称"></el-table-column>
        <el-table-column prop="supplierName" label="供应商"></el-table-column>
        <el-table-column prop="payType" label="支付类型"></el-table-column>
        <el-table-column prop="time" label="进货时间"></el-table-column>
        <el-table-column prop="username" label="操作人"></el-table-column>
        <el-table-column prop="num" label="进货数量"></el-table-column>
        <el-table-column prop="unit" label="商品规格"></el-table-column>
        <el-table-column prop="price" label="进货价格"></el-table-column>
        <el-table-column prop="totalPrice" label="进货总价格"></el-table-column>
        <el-table-column prop="comment" label="备注"></el-table-column>
        <el-table-column fixed="right" label="操作" align="center" width="240">
          <template v-slot="scope">
            <el-button size="mini" type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" plain @click="del(scope.row.id)">删除</el-button>
            <el-button size="mini" type="warning" plain @click="handleStockBack(scope.row)">退货</el-button>
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

    <el-dialog title="商品进货" :visible.sync="stockFormVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="供应商" prop="supplierId">
          <el-select style="width: 100%" v-model="form.supplierId" @change="getGoods" :disabled="form.id">
            <el-option v-for="item in suppliers" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品名称" prop="goodsId">
          <el-select style="width: 100%" v-model="form.goodsId">
            <el-option v-for="item in goods" :key="item.id" :value="item.id" :label="item.name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支付类型" prop="payType">
          <el-select style="width: 100%" v-model="form.payType">
            <el-option v-for="item in ['支付宝','微信','银联']" :key="item" :value="item" :label="item"></el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="进货时间" prop="time">
          <el-date-picker
              v-model="form.time"
              type="datetime"
              placeholder="选择日期时间"
              default-time="12:00:00">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="进货数量" prop="num">
          <el-input-number v-model="form.num" :precision="0" :min="1" :max="9999" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item label="商品规格" prop="unit">
          <el-input v-model="form.unit" placeholder="商品规格"></el-input>
        </el-form-item>
        <el-form-item label="进货单价" prop="price">
          <el-input-number v-model="form.price" :precision="2" :min="1" :max="9999" label="进货单价"></el-input-number>
        </el-form-item>

        <el-form-item label="备注" prop="comment">
          <el-input v-model="form.comment" placeholder="请输入备注"></el-input>
        </el-form-item>


      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="stockFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="商品退货" :visible.sync="stockBackFormVisible" width="40%" :close-on-click-modal="false"
               destroy-on-close>
      <el-form :model="stockBackForm" label-width="100px" style="padding-right: 50px" ref="formRef">

        <el-form-item label="退货数量" prop="num">
          <el-input-number v-model="stockBackForm.num" :precision="0" :min="1" :max="9999"
                           label="退货数量"></el-input-number>
        </el-form-item>


        <el-form-item label="备注" prop="comment">
          <el-input type="textarea" v-model="stockBackForm.comment" placeholder="请输入备注"></el-input>
        </el-form-item>


      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="stockBackFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="stockBackSave">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  addStockService,
  deleteStockBatchService,
  deleteStockService,
  updateStockService,
  stockList
} from '@/api/stock'
import {selectAllSupplier} from '@/api/supplier'
import {goodsList} from '@/api/goods'
import {addStockBackService} from "@/api/stockBack";


export default {
  name: "sale",
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

      stockBackFormVisible: false, //控制退货的弹窗的显示
      stockFormVisible: false, //控制进货的弹窗的显示
      form: {}, //商品进货表单
      stockBackForm: {}, //退货表单
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
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

    //根据供应商id获取对应的商品信息
    async getGoods() {
      if (this.form.supplierId) {
        let result = await goodsList({"supplierId": this.form.supplierId})
        this.goods = result.data.records
      }

    },

    //加载供应商信息
    async loadSupplier() {
      let result = await selectAllSupplier()
      this.suppliers = result.data
    },

    //加载商品的信息
    // async loadGoods() {
    //   let result = await selectAllGoods()
    //   this.goods = result.data
    // },

    //加载表格数据
    async load() {
      let result = await stockList(this.selectConditions, this.pageNum, this.pageSize)
      this.tableData = result.data.records
      this.total = result.data.total
    },

    //提交商品进货表单的申请，包括新增和修改
    save() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          try {
            if (this.form.id == null)
              await addStockService(this.form)
            else
              await updateStockService(this.form)
            this.$message.success('保存成功')
            this.stockFormVisible = false
            await this.load()
          } catch (e) {
          }
        }
      })
    },

    //提交退货表单的申请
    async stockBackSave() {
      console.log(this.stockBackForm)
      let result = await addStockBackService(this.stockBackForm)
      this.$message.success('退货成功')
      this.stockBackFormVisible = false
      this.load()
    },



    //删除
    del(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"})
          .then(async () => {
                try {
                  await deleteStockService(id)
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
        let result = await deleteStockBatchService(this.ids)
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

    //点击退货按钮时触发
    //将需要退货的商品信息传给退货表单
    handleStockBack(row) {
      this.stockBackForm = JSON.parse(JSON.stringify(row))
      this.stockBackForm.id = null //id不需要传给后端，因为后端的id是自增的，跟商品进货的id没有关系
      this.stockBackForm.time = null //退货时间和进货时间不一样的
      this.stockBackForm.num = 1 //设置退货数量的默认值1
      this.stockBackForm.comment = '' //设置退货的备注为‘’
      this.stockBackFormVisible = true
    },
    // 当前选中的所有的行数据
    handleSelectionChange(rows) {
      this.ids = rows.map(v => v.id)
    },

    // 新增数据
    handleAdd() {
      this.form = {}  // 新增数据的时候清空数据
      this.stockFormVisible = true   // 打开弹窗
    },

    // 编辑数据
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.stockFormVisible = true   // 打开弹窗
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
