<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >

      <div class="title-container">
        <h3 class="title">Vue Admin</h3>
      </div>

      <el-form-item
        prop="username"
        :class="{'apply-shake': shakeType.username}"
      >
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="username"
          v-model.trim="loginForm.username"
          placeholder="用户名"
          name="username"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-form-item
        prop="password"
        :class="{'apply-shake': shakeType.password}"
      >
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :key="passwordType"
          ref="password"
          v-model.trim="loginForm.password"
          :type="passwordType"
          placeholder="密码"
          name="password"
          tabindex="2"
          auto-complete="on"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
        </span>
      </el-form-item>
      <div class="validCodeBox">
        <el-form-item
          prop="verifyCode"
          class="validCode"
          :class="{'apply-shake': shakeType.verifyCode}"
        >
          <span class="svg-container">
            <svg-icon icon-class="validCode" />
          </span>
          <el-input
            ref="verifyCode"
            v-model.trim="loginForm.verifyCode"
            placeholder="验证码"
            name="verifyCode"
            type="text"
            tabindex="3"
            @keyup.enter.native="handleLogin"
          />
        </el-form-item>
        <img :src="codeImgSrc" class="validImg" alt="" @click="validChange">
      </div>
      <el-button
        :loading="loading"
        type="primary"
        style="width:100%;margin-bottom:20px;font-size: 18px;margin-top: 14px"
        @click.native.prevent="handleLogin"
      >登 录
      </el-button>
    </el-form>
  </div>
</template>

<script>
import encryp from '@/utils/password/encryp.js'
import { verifyCodeUrl, login } from '~/auth/auth'
export default {
  name: 'Login',
  data() {
    const nameShakeType = (rule, value, callback) => {
      if (!value) {
        this.usernameShakeType = true
        return callback('请输入用户名')
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        this.passwordShackType = true
        return callback('请输入密码')
      }
      if (value.length < 5) {
        this.passwordShackType = true
        callback(new Error('密码不能小于6位'))
      } else {
        callback()
      }
    }
    const verifyCodeShakeType = (rule, value, callback) => {
      if (!value) {
        this.verifyCodeShakeType = true
        return callback('请输入验证码')
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        username: '',
        password: '',
        verifyCode: '',
        verifyCodeKey: ''
      },
      loginRules: {
        username: [{ trigger: 'blur', validator: nameShakeType }],
        password: [{ trigger: 'blur', validator: validatePassword }],
        verifyCode: [{ trigger: 'blur', validator: verifyCodeShakeType }]
      },
      loading: false,
      passwordType: 'password',
      redirect: '/system/dashboard',
      codeImgSrc: '/api/' + verifyCodeUrl + '?random=' + Math.random() * 100,
      shakeType: {
        username: false,
        password: false,
        verifyCode: false
      },
      usernameShakeType: false,
      passwordShackType: false,
      verifyCodeShakeType: false
    }
  },
  watch: {
    // $route: {
    //   handler: function(route) {
    //     this.redirect = route.query && route.query.redirect
    //   },
    //   immediate: true
    // }
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    validChange() {
      this.codeImgSrc = '/api' + verifyCodeUrl + '?random=' + Math.random() * 100
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loginForm.verifyCodeKey = this.$cookies.get('verifyCodeKey')
          console.log(this.loginForm.password)
          const data = {
            'username': this.loginForm.username,
            'password': encryp(this.loginForm.password),
            'code': this.loginForm.verifyCode,
            'uuid': this.loginForm.verifyCodeKey
          }
          console.log(data)
          login(data).then(() => {
            // this.$router.push(`/bigScreen?redirect=${this.$route.fullPath}`)
            this.$router.push({ path: this.redirect || '/system/index' })
            // const path = this.redirect || '/system/index'
            // console.log(path)
            this.loading = false
          }).catch(() => {
            this.shakeType.verifyCode = true
            setTimeout(() => {
              this.shakeType.verifyCode = false
            }, 820)
            this.validChange()
            this.loading = false
          })

          // this.loading = true
          // this.$store.dispatch('user/login', data).then(() => {
          //   // this.$router.push(`/bigScreen?redirect=${this.$route.fullPath}`)
          //   this.$router.push({ path: this.redirect || '/system/index' })
          //   // const path = this.redirect || '/system/index'
          //   // console.log(path)
          //   this.loading = false
          // }).catch(() => {
          //   this.shakeType.verifyCode = true
          //   setTimeout(() => {
          //     this.shakeType.verifyCode = false
          //   }, 820)
          //   this.validChange()
          //   this.loading = false
          // })
        } else {
          this.shakeType = {
            username: this.usernameShakeType,
            password: this.passwordShackType,
            verifyCode: this.verifyCodeShakeType
          }
          setTimeout(() => {
            this.shakeType = {
              username: false,
              password: false,
              verifyCode: false
            }
            this.usernameShakeType = false
            this.passwordShackType = false
            this.verifyCodeShakeType = false
          }, 820)
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #283443;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: $light_gray;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $cursor;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $light_gray inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: #fff;
    border-radius: 5px;
    color: #2d3a4b;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #fff;

.login-container {
  min-height: 100%;
  width: 100%;
  background-image: url("../../img/bg.jpg");
  background-size: cover;
  overflow: hidden;

  .login-form {
    border: 1px solid rgba(156, 156, 156, 0.5);
    border-radius: 10px;
    background: rgba(0, 0, 0, 0.4);
    position: absolute;
    float: right;
    width: 520px;
    height: 430px;
    max-width: 100%;
    padding: 40px;
    top: 50%;
    right: 0;
    margin: -185px 10% auto auto;
    overflow: hidden;
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $bg;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 24px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: normal;
      /*font-weight: bold;*/
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $bg;
    cursor: pointer;
    user-select: none;
  }
  .validCodeBox {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 22px;
  }
  .validCode {
    width: 70%;
    display: inline-block;
    margin-bottom: 0;
  }
  .validImg {
    cursor: pointer;
  }
}@keyframes shake {
   10%, 90% {
     transform: translate3d(-1px, 0, 0);
   }

   20%, 80% {
     transform: translate3d(2px, 0, 0);
   }

   30%, 50%, 70% {
     transform: translate3d(-4px, 0, 0);
   }

   40%, 60% {
     transform: translate3d(4px, 0, 0);
   }
 }

.apply-shake {
  animation: shake 0.82s cubic-bezier(.36,.07,.19,.97) both;
}
</style>
