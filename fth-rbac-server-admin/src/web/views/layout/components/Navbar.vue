<template>
  <div>
    <el-menu class="navbar" mode="horizontal">
      <breadcrumb></breadcrumb>
      <el-tooltip effect="dark" content="全屏" placement="bottom">
        <screenfull class="screenfull right-menu-item"></screenfull>
      </el-tooltip>
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <!--          <img class="user-avatar" :src="avatar+'?imageView2/1/w/80/h/80'">-->
          <div class="username-avatar">{{user.username | substr(1)}}</div>
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu class="user-dropdown" slot="dropdown">
          <router-link class="inlineBlock" to="/">
            <el-dropdown-item>
              主页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span @click="userDetailDialog" style="display:block;">编辑资料</span>
          </el-dropdown-item>
          <el-dropdown-item divided>
            <span @click="logout" style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-menu>
    <el-dialog class="modify-user-dialog" title="编辑个人资料" :visible.sync="dialogFormVisible" center>
      <el-form :model="userForm" :rules="rules" ref="userForm">
        <el-form-item label="用户名" label-width="60px" prop="userName">
          <el-input v-model="userForm.userName" auto-complete="off" maxlength="32" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="姓名" label-width="60px" prop="realName">
          <el-input v-model="userForm.realName" auto-complete="off" maxlength="180"
                    placeholder="请输入真实姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号" label-width="60px" prop="phone">
          <el-input v-model="userForm.phone" auto-complete="off" maxlength="16"
                    placeholder="请输入手机号"></el-input>
        </el-form-item>

        <el-form-item label="密码" label-width="60px" prop="pass">
          <el-input type="password" v-model="userForm.password" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" label-width="60px" prop="checkPass">
          <el-input type="password" v-model="userForm.checkPass" auto-complete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUserForm">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import Breadcrumb from '@web/components/Breadcrumb';
import Screenfull from '@web/components/Screenfull';


export default {
  components: {
    Screenfull,
    Breadcrumb
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.userForm.checkPass !== '') {
          this.$refs.userForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.userForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      dialogFormVisible: false,
      userForm: {
        id: null,
        userName: '',
        realName: '',
        phone: '',
        userType: null,
        password: '',
        checkPass: ''
      },
      rules: {
        userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
        phone: [{ required: true, message: '手机号不能为空' }],
        pass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
      },
    };
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'user'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar');
    },
    logout() {
      console.log('登出？');
      this.$store.dispatch('LogOut').then(() => {
        location.reload(); // 为了重新实例化vue-router对象 避免bug
      });
    },
    userDetailDialog() {
      this.dialogFormVisible = true;
      getUserInfo(getToken).then(res => {
        const { code, content: { id, userName, realName, userType, phone, password } } = res;
        if (code === 200) {
          this.userForm.id = id;
          this.userForm.userName = userName;
          this.userForm.realName = realName;
          this.userForm.phone = phone;
          this.userForm.userType = userType;
          this.userForm.password = password;
          this.userForm.checkPass = password;
        }
      }).catch(function (err) {
        console.log(err.message);
      });
    },
    updateUserForm() {
      update(this.userForm).then(res => {
          const { code, msg } = res;
          if (code === 200) {
            this.$message({
              message: '修改成功',
              type: 'success'
            });
            this.dialogFormVisible = false;
          } else {
            this.$message({
              message: msg,
              type: 'error'
            });
          }
        }
      ).catch(function (err) {
        console.log(err.message);
      });
    }
  }
};
</script>

<style lang="less" scoped>
  .navbar {
    height: 50px;
    line-height: 50px;
    border-radius: 0px !important;

    .hamburger-container {
      line-height: 58px;
      height: 50px;
      float: left;
      padding: 0 10px;
    }

    .screenfull {
      position: absolute;
      right: 90px;
      top: 16px;
      color: red;
    }

    .avatar-container {
      height: 50px;
      display: inline-block;
      position: absolute;
      right: 35px;

      .avatar-wrapper {
        cursor: pointer;
        height: 100%;
        display: flex;
        align-items: center;
        position: relative;

        .username-avatar {
          background: #304156;
          color: #fff;
          text-transform: uppercase;
          border-radius: 50%;
          width: 30px;
          height: 30px;
          text-align: center;
          line-height: 30px;
        }

        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }

  .modify-user-dialog {
    .el-input {
      width: 80%;
    }
  }
</style>

