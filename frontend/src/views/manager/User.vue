<template>
  <div>
    <div class="search">
      <el-input placeholder="请输入用户名查询" style="width: 200px;margin-right: 10px"
                v-model="selectConditions.username"></el-input>
      <el-input placeholder="请输入姓名查询" style="width: 200px;margin-right: 10px"
                v-model="selectConditions.name"></el-input>
      <el-select v-model="selectConditions.sex" placeholder="性别" style="margin-right: 10px">
        <el-option
            v-for="item in ['男','女']"
            :key="item"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-select v-model="selectConditions.status" placeholder="帐号状态" style="margin-right: 10px">
        <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="selectConditions.departmentId" placeholder="部门名称">
        <el-option
            v-for="item in departments"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-button type="info" plain style="margin-left: 10px" @click="load()">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">重置</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" style="width: 100%" height="500" strip @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="id" label="序号" width="70" align="center" sortable></el-table-column>
        <el-table-column label="头像">
          <template v-slot="scope">
            <div style="display: flex; align-items: center">
              <el-image style="width: 40px; height: 40px; border-radius: 50%" v-if="scope.row.avatar"
                        :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="role" label="权限"></el-table-column>

        <el-table-column prop="status" label="账号状态">
          <template v-slot="scope">
            <el-tag type="success" v-if="scope.row.status">启用</el-tag>
            <el-tag type="danger" v-else>禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="departmentName" label="部门"></el-table-column>
        <el-table-column prop="phone" label="电话"></el-table-column>
        <el-table-column prop="email" label="邮箱"></el-table-column>
        <el-table-column prop="sex" label="性别"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
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

    <el-dialog title="用户" :visible.sync="formVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="用户名" :disabled="form.id"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="phone">
          <el-input v-model="form.age" placeholder="年龄"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="departmentId">
          <el-select v-model="form.departmentId" placeholder="部门" style="width: 100%">
            <el-option v-for="item in departments" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="地址"></el-input>
        </el-form-item>

        <el-form-item label="账号状态" prop="status">
          <el-switch
              v-model="form.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用">
          </el-switch>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/upload'"
              :headers="{ authorization: user.token }"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传头像</el-button>
          </el-upload>
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
import {updateUserService, addUserService, deleteUserService, userList, deleteUserBatchService} from '@/api/user'
import {selectAllDepartment} from '@/api/department'


export default {
  name: "user",
  data() {
    return {
      departments: [],//所有的部门信息
      tableData: [],  // 所有的数据
      pageNum: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      total: 0, //总记录数
      selectConditions: {
        username: '',
        name: '',
        status: null,
        sex: '',
        departmentId: null
      },
      sexOptions: [{value: '男', label: '男'},
        {value: '女', label: '女'}],
      statusOptions: [{value: true, label: '启用'},
        {value: false, label: '禁用'}],
      accountStatus: null,
      formVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
        departmentId: [{required: true, message: '请选择部门', trigger: 'blur'}],
      },
      ids: []
    }
  },
  created() {
    this.load(this.pageNum, this.pageSize)
    this.loadDepartment()
  },
  methods: {

    //加载表格数据
    async load() {
      let result = await userList(this.selectConditions, this.pageNum, this.pageSize)
      this.tableData = result.data.records
      this.total = result.data.total
    },
    //加载部门数据
    async loadDepartment() {
      let result = await selectAllDepartment()
      this.departments = result.data
      console.log(this.departments)
    },


    save() {
      this.$refs.formRef.validate(async (valid) => {
        if (valid) {
          try {
            if (this.form.id == null)
              await addUserService(this.form)
            else
              await updateUserService(this.form)
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
    delBatch() {   // 批量删除
      if (!this.ids.length) {
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('您确定批量删除这些数据吗？', '确认删除', {type: "warning"}).then(async () => {
        await deleteUserBatchService(this.ids)
        this.$message.success("批量删除成功")
        this.load()
      }).catch(() => {
      })
    },
    handleSelectionChange(rows) {   // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },

    //点击重置按钮触发
    reset() {
     this.selectConditions= {}
      this.load()
    },

    //点击新增按钮触发
    handleAdd() {   // 新增数据
      this.form = {status:true}  // 新增数据的时候清空数据,同时给status赋初始值true
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
    handleAvatarSuccess(response, file, fileList) {
      // 把头像属性换成上传的图片的链接
      this.form.avatar = response.data
    },
  }
}
</script>

<style scoped>

</style>
