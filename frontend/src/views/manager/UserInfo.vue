<template>
  <div>
    <el-card style="width: 70%;margin: 50px auto" >
      <el-form :model="userInfo" label-width="100px" style="padding-right: 50px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              :action="$baseUrl + '/files/upload'"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="userInfo.avatar" :src="userInfo.avatar" class="avatar"/>
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userInfo.username" placeholder="用户名" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="userInfo.name" placeholder="昵称" disabled></el-input>
        </el-form-item>
        <el-form-item label="权限" prop="role">
          <el-input v-model="userInfo.role" placeholder="权限" disabled></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="departmentId">
          <el-input v-model="userInfo.departmentName" disabled></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="userInfo.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userInfo.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="userInfo.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="userInfo.age" placeholder="年龄"></el-input>
        </el-form-item>


        <el-form-item label="地址" prop="address">
          <el-input v-model="userInfo.address"></el-input>
        </el-form-item>

      </el-form>
    </el-card>
  </div>
</template>

<script>
import {userInfoService} from '@/api/user'

export default {
  name: "UserInfo",
  data() {
    const {username} = JSON.parse(localStorage.getItem('xm-user') || '{}')
    return {
      // user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      username:  username,
      userInfo: {}
    }
  },
  created() {
    this.getUser()
  },
  methods: {
    update() {
      // 保存当前的用户信息到数据库
      this.$request.put('/staff/update', this.user).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')

          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.user, 'avatar', response.data)
    },
    async getUser() {
      let result = await userInfoService(this.username)
      this.userInfo = result.data
      console.log(this.userInfo)
    }
  }
}
</script>

<style scoped>
/deep/ .el-form-item__label {
  font-weight: bold;
}

/deep/ .el-upload {
  border-radius: 50%;
}

/deep/ .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}

/deep/ .avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
</style>
