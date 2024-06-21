<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入用户名查询" style="width: 200px;margin-right: 10px"
                v-model="selectConditions.username"></el-input>



      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" style="width: 100%" height="500" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column prop="title" label="业务模块"></el-table-column>
        <el-table-column prop="operation" label="操作类型"></el-table-column>
        <el-table-column prop="target" label="操作对象"></el-table-column>
        <el-table-column prop="msg" label="操作结果"></el-table-column>

        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="ip" label="ip地址"></el-table-column>
        <el-table-column prop="time" label="时间" sortable></el-table-column>
        <el-table-column fixed="right" label="操作" align="center" width="180">
          <template v-slot="scope">
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
  </div>
</template>

<script>
import {logList} from '@/api/log'


export default {
  name: "log",
  data() {
    return {
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0, //总记录数
      selectConditions: {
        username: '',
        status: null,
        sex: '',
        departmentId: null
      },
      user: JSON.parse(localStorage.getItem('xm-Log') || '{}'),
      ids: []
    }
  },
  created() {
    this.load(this.pageNum, this.pageSize)
  },
  methods: {




    del(id) {
      this.$confirm('您确定删除吗？', '确认删除', {type: "warning"})
          .then(async () => {
                try {
                  await deleteUserService(id)
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
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },

    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(async () => {

        let result = await deleteUserBatchService(this.ids)
        this.$message.success("批量删除成功")
        this.load()
      }).catch(() => {
      })
    },

    async load() {
      let result = await logList(this.selectConditions, this.pageNum, this.pageSize)
      this.tableData = result.data.records
      this.total = result.data.total
    },

    reset() {
     this.selectConditions= {}
      this.load()
    },

    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.load()
    },

  }
}
</script>

<style scoped>

</style>
