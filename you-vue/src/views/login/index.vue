<script setup lang="ts">

import {reactive, ref} from "vue";
import type {FormRules} from "element-plus";
import {Key, Lock, User} from "@element-plus/icons-vue";

// 登录model
const loginModel = reactive({
  username: "",
  password: "",
  verifyCode: "",
  verifyCodeKey: ""
})

// 登录表单校验
const loginFormRules: FormRules = {
  username: [{required: true, trigger: "blur", message: "请输入用户名"}],
  password: [{required: true, trigger: "blur", message: "请输入密码"}],
  verifyCode: [{required: true, trigger: "blur", message: "请输入验证码"}]
}

// 登录按钮loading
const loading = ref(false)

</script>

<template>
  <div class="login-container">
    <div class="login-card">
      <div class="title">
        <h3>you vue</h3>
      </div>
      <div class="content">
        <el-form :model="loginModel" :rules="loginFormRules" class="login-form" autocapitalize="on"
                 label-position="left">

          <el-form-item prop="username">
            <el-input v-model.trim="loginModel.username" placeholder="用户名" type="text" tabindex="1"
                      :prefix-icon="User"
                      clearable size="large"/>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model.trim="loginModel.password" placeholder="密码" type="password" tabindex="2"
                      :prefix-icon="Lock" clearable size="large"/>
          </el-form-item>
          <div class="valid-code-box">
            <el-form-item prop="verifyCode">
              <el-input v-model.r.trim="loginModel.verifyCode" placeholder="验证码" type="text" tabindex="3"
                        :prefix-icon="Key" clearable size="large"/>
            </el-form-item>
          </div>
          <el-button :loading="loading" type="primary" size="large">登录</el-button>
        </el-form>
      </div>
    </div>

  </div>
</template>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: right;
  align-items: center;
  min-height: 100%;
  width: 100%;
  background-image: url("@/assets/login/bg.jpg");
  background-size: cover;

  .login-card {
    width: 480px;
    border-radius: 20px;
    box-shadow: 0 0 10px #dcdfe6;
    background-color: #fff;
    overflow: hidden;
    margin-right: 10%;

    .title {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 120px;
    }

    .content {
      padding: 20px 50px 50px 50px;

      :deep(.el-input-group__append) {
        padding: 0;
        overflow: hidden;

        .el-image {
          width: 100px;
          height: 40px;
          border-left: 0;
          user-select: none;
          cursor: pointer;
          text-align: center;
        }
      }

      .el-button {
        width: 100%;
        margin-top: 10px;
      }
    }
  }
}
</style>