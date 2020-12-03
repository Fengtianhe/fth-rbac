<template>
  <div>
    <el-menu class="navbar" mode="horizontal">
      <breadcrumb></breadcrumb>
      <el-tooltip effect="dark" content="全屏" placement="bottom">
        <screenfull class="screenfull right-menu-item"></screenfull>
      </el-tooltip>
      <el-dropdown class="avatar-container" trigger="click" @command="handleUserDropdown">
        <div class="avatar-wrapper">
          <!--          <img class="user-avatar" :src="avatar+'?imageView2/1/w/80/h/80'">-->
          <div class="username-avatar">{{user.nickname || user.username | substr(1)}}</div>
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu class="user-dropdown" slot="dropdown">
          <el-dropdown-item v-for="dp in userDropdown" :key="dp.command" :command="dp.command" :divided="dp.divided">
            {{dp.label}}
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-menu>

    <!--  更新个人资料  -->
    <el-dialog class="modify-user-dialog" :visible.sync="dialogFormVisible">
      <div class="userform-avatar">
        <div class="username-avatar">{{user.nickname || user.username | substr(1)}}</div>
        <div class="username">{{user.username}}</div>
      </div>
      <el-form :model="userForm" ref="userForm" label-width="80px" class="userinfo-form">
        <el-form-item label="姓名">
          <el-input v-model="userForm.nickname" maxlength="180" placeholder="请输入真实姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="userForm.phone" maxlength="11" placeholder="请输入手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUserForm">提 交</el-button>
      </div>
    </el-dialog>

    <!--  修改密码  -->
    <el-dialog class="modify-user-dialog" title="修改密码" :visible.sync="uptPwdDialog" center>
      <el-form :model="uptPwdForm" :rules="uptPwdRules" ref="uptPwdForm" label-width="80px">
        <el-form-item label="当前密码" prop="password">
          <el-input v-model="uptPwdForm.username" placeholder="请输入当前密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="uptPwdForm.realName" placeholder="请输入新密码"></el-input>
        </el-form-item>
        <el-form-item label="重新输入" prop="checkPass">
          <el-input v-model="uptPwdForm.phone" placeholder="请输入重新输入新密码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="uptPwdDialog = false">取 消</el-button>
        <el-button type="primary" @click="uptPwd()">提 交</el-button>
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
        if (this.uptPwdForm.checkPass !== '') {
          this.$refs.uptPwdForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.uptPwdForm.newPass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      userDropdown: [
        { command: 'UPDATE', label: '编辑资料', divided: false },
        { command: 'PASSWORD', label: '修改密码', divided: false },
        { command: 'LOGOUT', label: '退出登录', divided: true }
      ],
      dialogFormVisible: false,
      userForm: {
        nickname: '',
        phone: ''
      },
      uptPwdDialog: false,
      uptPwdForm: {
        password: '',
        newPass: '',
        checkPass: ''
      },
      uptPwdRules: {
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        newPass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ]
      }
    };
  },
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  methods: {
    handleUserDropdown(command) {
      console.log(command);
      switch (command) {
        case 'UPDATE':
          this.userDetailDialog();
          break;
        case 'PASSWORD':
          this.$refs['uptPwdForm'].resetField();
          this.uptPwdDialog();
          break;
        case 'LOGOUT':
          this.logout();
          break;
        default:
          break;
      }
    },
    logout() {
      this.$store.dispatch('LOGOUT');
      this.$router.push({ path: '/login' });
    },
    userDetailDialog() {
      this.dialogFormVisible = true;
      const { nickname, phone } = this.user;
      this.userForm = {
        nickname,
        phone,
      };
    },
    updateUserForm() {
      this.$store.dispatch('UPT_USER', this.userForm).then(() => {
        this.dialogFormVisible = false;
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

  .modify-user-dialog {

    .userform-avatar {
      display: flex;
      flex-direction: column;
      align-items: center;

      .username-avatar {
        width: 70px;
        height: 70px;
        font-size: 30px;
        line-height: 70px;
      }

      .username {
        margin-top: 20px;
      }
    }

    .userinfo-form {
      margin-top: 20px;
    }
  }
</style>

