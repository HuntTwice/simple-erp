<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入供应商名查询" style="width: 200px;margin-right: 10px"
                v-model="selectConditions.name"></el-input>
      <el-select v-model="selectConditions.status" placeholder="供应商状态">
        <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
      <el-upload style="display: inline-block;margin:auto 10px" :action="$baseUrl+'/suppliers/import'"
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
        <el-table-column prop="name" label="供应商名"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="zipCode" label="邮编"></el-table-column>
        <el-table-column prop="user" label="联系人"></el-table-column>
        <el-table-column prop="userTel" label="联系人电话"></el-table-column>
        <el-table-column prop="bank" label="开户银行"></el-table-column>
        <el-table-column prop="bankCard" label="银行卡号"></el-table-column>
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

    <el-dialog title="供应商" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="供应商名" prop="name">
          <el-input v-model="form.name" placeholder="供应商名" :disabled="form.id"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="邮编" prop="zipCode">
          <el-input v-model="form.zipCode" placeholder="邮编"></el-input>
        </el-form-item>
        <el-form-item label="联系人" prop="user">
          <el-input v-model="form.user" placeholder="联系人"></el-input>
        </el-form-item>
        <el-form-item label="联系人电话" prop="userTel">
          <el-input v-model="form.userTel" placeholder="联系人电话"></el-input>
        </el-form-item>
        <el-form-item label="开户银行" prop="bank">
          <el-input v-model="form.bank" placeholder="开户银行"></el-input>
        </el-form-item>

        <el-form-item label="银行卡号" prop="bankCard">
          <el-input v-model="form.bankCard" placeholder="银行卡号"></el-input>
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
  addSupplierService,
  deleteSupplierBatchService,
  deleteSupplierService,
  updateSupplierService,
  supplierList,
  exportBatch
} from '@/api/supplier'
import {Message} from "element-ui";

export default {
  name: "supplier",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0,
      selectConditions: {
        name: '',
        status: null,
        user: '',
      },
      statusOptions: [{value: true, label: '启用'},
        {value: false, label: '禁用'}],
      formVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        name: [{required: true, message: '请输入供应商名称', trigger: 'blur'}],
      },
      ids: []
    }
  },
  created() {
    this.load(this.pageNum, this.pageSize)
  },
  methods: {
    //批量导出
    async expBatch() {
      debugger

      // try {
      //   const response = await fetch(this.$baseUrl + '/suppliers/export', {
      //     method: 'GET',
      //     headers: {"Authorization":this.user.token}
      //   });
      //   const blob = await response.blob();
      //
      try {
        let result = await exportBatch()
        debugger
        let blob = result.data
        debugger
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
    async load() {
      let result = await supplierList(this.selectConditions, this.pageNum, this.pageSize)
      this.tableData = result.data.records
      console.log(this.tableData)
      this.total = result.data.total
    },

    save() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          try {
            if (this.form.id == null)
              await addSupplierService(this.form)
            else
              await updateSupplierService(this.form)
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
                  await deleteSupplierService(id)
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
        let result = await deleteSupplierBatchService(this.ids)
        this.$message.success("批量删除成功")
        this.load()
      }).catch(() => {
      })
    },


    reset() {
      this.selectConditions.name = ''
      this.selectConditions.status = null
      this.load()
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },
    handleAdd() {   // 新增数据
      this.form = {}  // 新增数据的时候清空数据
      this.formVisible = true   // 打开弹窗
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.formVisible = true   // 打开弹窗
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
    }
  }
}
</script>

<style scoped>

</style>
