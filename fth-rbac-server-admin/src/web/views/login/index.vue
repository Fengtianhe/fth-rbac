<template>
  <div class="main">
    <div class="header box">
      <div class="header-container">
        <div class="header-title">基于资源的多平台权限管理系统</div>
        <div class="header-img"></div>
      </div>
    </div>
    <div class="center-container box">
      <div class="center-left"></div>
      <div class="center-right">
        <div class="login-form"></div>
        <el-form
                ref="loginForm"
                :model="loginForm"
                :rules="loginRules"
                class="login-form"
                auto-complete="on"
                label-position="left"
        >
          <el-form-item prop="username">
            <el-input
                    ref="username"
                    v-model="loginForm.username"
                    placeholder="请输入用户名"
                    name="username" type="text"
                    tabindex="1"
                    auto-complete="on"
                    prefix-icon="el-icon-user-solid"
                    @blur="getCode()"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                    :key="passwordType"
                    ref="password"
                    v-model="loginForm.password"
                    :type="passwordType"
                    placeholder="请输入密码"
                    name="password"
                    tabindex="2"
                    auto-complete="on"
                    @keyup.enter.native="handleLogin"
                    prefix-icon="el-icon-monitor"/>
          </el-form-item>
          <el-checkbox v-model="checked" style="color: white;" @change="showPwd">显示密码</el-checkbox>

          <div class="box" v-show="showCaptcha">
            <div class="verify-code">
              <el-input
                      v-model="loginForm.captcha"
                      placeholder="不区分大小写"
                      @keyup.enter.native="handleLogin"
              ></el-input>
            </div>
            <div class="verify-img">
              <div class="img-code">
                <img :src="getCode" alt="二维码" id="code" @click="getCode">
              </div>
            </div>
          </div>

          <el-button :loading="loading" type="primary" style="width: 100%; margin-top: 10px;"
                     @click.native.prevent="handleLogin">登录
          </el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery';
import { UserService } from '@web/service';
import md5 from 'md5';

export default {
  name: 'Login',
  data() {
    return {
      loading: false,
      loginForm: {
        username: '',
        password: '',
        captcha: ''
      },
      showCaptcha: false,
      loginRules: {},
      passwordType: 'password',
      checked: false
    };
  },
  mounted() {
  },
  methods: {
    getCode() {
      if (!this.loginForm.username) {
        return;
      }
      const that = this;
      let url = `/api/user/captcha?username=${this.loginForm.username}`;
      let xhr = new XMLHttpRequest();
      xhr.open('GET', url, true);
      xhr.responseType = 'blob';
      xhr.onload = function () {
        if (this.status === 200) {
          let res = this.response;
          $('#code').attr('src', window.URL.createObjectURL(res));
          that.showCaptcha = true;
        }
      };
      xhr.send();
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = '';
      } else {
        this.passwordType = 'password';
      }
      this.$nextTick(() => {
        this.$refs.password.focus();
      });
    },
    async handleLogin() {
      this.loginForm.password = md5(this.loginForm.password);
      this.loading = true;
      try {
        const response = await UserService.login(this.loginForm);
        if (response.code === 200) {
          this.$store.commit('SET_TOKEN', response.data);
          await UserService.info();
          this.$store.commit('SET_MENUS');
          this.$router.push({ path: '/' });
        } else if (response.code === 604) {
          this.getCode();
          this.loginForm.captcha = '';
        }
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style rel="stylesheet/less" lang="less">
  .img-code {
    margin: 5px 0 0 5px;
    width: 200px;
    height: 40px;
  }

  .verify-code {
    width: 40%;
    height: 50px;
    margin-top: 5px;
  }

  .verify-img {
    width: 60%;
    height: 50px;
  }

  .main {
    width: 100%;
    height: 100%;
    background: url("../../../common/assets/images/bj.jpg");
    overflow: hidden;
  }

  .header {
    width: 100%;
    height: 180px;
  }

  .box {
    display: -webkit-flex;
    display: flex;
    justify-content: center;
  }

  .header-container {
    /*border: 1px solid pink;*/
  }

  .header-title {
    text-align: center;
    color: #ffffff;
    font-size: 32px;
    line-height: 84px;
  }

  .header-img {
    background: url("../../../common/assets/images/head.gif") no-repeat center center;
    background-size: 100%;
    height: 96px;
    width: 960px;
  }

  .center-container {
    width: 100%;
    height: 464px;
  }

  .center-left {
    width: 491px;
    height: 464px;
    background: url("../../../common/assets/images/d.png") no-repeat;
    background-size: 100% 100%;
  }

  @-webkit-keyframes rotation {
    from {
      -webkit-transform: rotate(0deg);
    }
    to {
      -webkit-transform: rotate(360deg);
    }
  }

  .center-container .center-left {
    -webkit-transform: rotate(360deg);
    animation: rotation 15s linear infinite;
    -moz-animation: rotation 15s linear infinite;
    -webkit-animation: rotation 15s linear infinite;
    -o-animation: rotation 15s linear infinite;
  }

  .center-right {
    width: 432px;
    height: 464px;
    background: url("../../../common/assets/images/e.png") no-repeat;
    background-size: 100% 100%;
    margin-left: 200px;
  }

  .login-form {
    width: 80%;
    margin: 150px 0 0 10%;
  }
</style>
