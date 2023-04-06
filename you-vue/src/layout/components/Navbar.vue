<template>
  <div class="navbar">
    <hamburger v-if="menuType === 'left'" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb v-if="menuType === 'left'" class="breadcrumb-container" />
    <el-menu
      v-if="menuType === 'top'"
      :default-active="activeMenu"
      :unique-opened="true"
      :collapse-transition="false"
      mode="horizontal"
      class="topMenu"
    >
      <sidebar-item v-for="route in fullRoutes" :key="route.path" :item="route" :base-path="route.path" />
    </el-menu>
    <div class="right-menu">
      <span style="cursor: pointer" :title="menuType === 'top'? '切换侧边菜单': '切换头部菜单'" @click="changeMenu">{{ menuType === "top"? "侧": "头" }}</span>
      <span style="cursor: pointer" title="显示大屏" @click="changeScreen">{{ showScreen === true? "隐": "显" }}</span>
      <!--      <router-link to="/" class="back-home">系统首页   </router-link>-->
      <el-dropdown class="avatar-container" trigger="click">
        <span class="user-name">{{ name }}</span>
        <i class="el-icon-caret-bottom" />
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="">
            <el-dropdown-item>
              <span style="display:block;" @click="changePassword">修改密码</span>
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog width="30%" :visible.sync="dialogVisible" :append-to-body="true" :destroy-on-close="true" title="修改密码">
      <el-form
        ref="dataForm"
        :model="dataForm"
        :rules="rules"
        label-position="left"
        label-width="80px"
        style="overflow: hidden"
      >
        <el-row :gutter="30">
          <el-col :span="24">
            <el-form-item
              label="原密码"
              prop="oldPassword"
            >
              <el-input v-model.trim="dataForm.oldPassword" type="password" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="24">
            <el-form-item
              label="新密码"
              prop="newPassword"
            >
              <el-input v-model="dataForm.newPassword" type="password" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="24">
            <el-form-item
              label="确认密码"
              prop="passwordAgain"
            >
              <el-input v-model="dataForm.passwordAgain" type="password" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogClose">
          取消
        </el-button>
        <el-button type="primary" @click="submitForm('dataForm')">
          提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import SidebarItem from './Sidebar/SidebarItem'
import { changePassword } from '../../api/system/user' // 校验
import md5 from 'md5'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    SidebarItem
  },
  data() {
    const validatePass = (rule, value, callback) => {
      if (this.dataForm.passwordAgain !== '') {
        this.$refs.dataForm.validateField('passwordAgain')
      }
      callback()
    }
    const validatePassAgain = (rule, value, callback) => {
      if (value !== this.dataForm.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      dataForm: {
        oldPassword: '',
        newPassword: '',
        passwordAgain: ''
      },
      dialogVisible: false,
      rules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
          { min: 6, max: 16, message: '原密码长度6至16位', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '密码长度6至16位', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ],
        passwordAgain: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validatePassAgain, trigger: ['blur', 'blur'] }
        ]
      }
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name',
      'fullRoutes'
    ]),
    menuType() {
      localStorage.getItem('menuType') !== null ? this.$store.state.settings.menuType = localStorage.getItem('menuType') : ''
      return this.$store.state.settings.menuType
    },
    showScreen() {
      return this.$store.state.settings.showScreen
    },
    activeMenu() {
      const route = this.$route
      const { meta, path } = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    }
  },
  methods: {
    changeMenu() {
      if (localStorage.getItem('menuType')) {
        localStorage.getItem('menuType') === 'left' ? localStorage.setItem('menuType', 'top') : localStorage.setItem('menuType', 'left')
      } else {
        this.$store.state.settings.menuType === 'left' ? localStorage.setItem('menuType', 'top') : localStorage.setItem('menuType', 'left')
      }
      this.$store.state.settings.menuType = localStorage.getItem('menuType')
    },
    changeScreen() {
      if (localStorage.getItem('showScreen')) {
        localStorage.getItem('showScreen') === 'true' ? localStorage.setItem('showScreen', 'false') : localStorage.setItem('showScreen', 'true')
      } else {
        this.$store.state.settings.showScreen === 'true' ? localStorage.setItem('showScreen', 'false') : localStorage.setItem('showScreen', 'true')
      }
      this.$store.state.settings.showScreen = localStorage.getItem('showScreen')
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    changePassword() {
      this.dialogVisible = true
    },
    dialogClose() {
      this.dialogVisible = false
    },
    submitForm(dataForm) {
      this.$refs[dataForm].validate((valid) => {
        if (valid) {
          const password = {
            oldPassword: md5(this.dataForm.oldPassword),
            newPassword: md5(this.dataForm.newPassword)
          }
          changePassword(password).then(res => {
            this.$message({
              type: 'success',
              message: '提交成功!'
            })
            this.dialogClose()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #ffffff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background .3s;
      -webkit-tap-highlight-color: transparent;

      &:hover {
        background: rgba(0, 0, 0, .025)
      }
    }
    .el-dropdown {
      color: black;
    }

    .el-dropdown-selfdefine {
      cursor: pointer;
    }

    .breadcrumb-container {
      float: left;
    }

    .right-menu {
      float: right;
      height: 100%;
      line-height: 50px;

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          margin-top: 5px;
          position: relative;

          .user-name {
            cursor: pointer;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 20px;
            font-size: 12px;
          }
        }
      }
    }
    .topMenu {
      width: 80%;
      float: left;
      display: flex;
      height: 50px;
    }
    .topMenu >>> .el-menu-item {
      height: 50px;
      line-height: 50px;
    }
    .topMenu >>> .el-submenu__title {
      height: 50px;
      line-height: 50px;
    }
    .topMenu >>> .el-submenu__icon-arrow {
      position: relative;
      vertical-align: middle;
      top: 0;
      right: 0;
      margin-top: 0;
      margin-right: 0;
      margin-left: 5px;
    }
  }
  .back-home{
    font-size: 14px;
    color: rgba(255, 255, 255, 0.9);
    margin-right: 20px;
  }
  .back-home:hover{
    color: #fff;
  }
  body >>> .el-menu--horizontal {
    margin-right: 8px;
  }
  .el-menu--horizontal >>> i {
    margin-right: 8px;
  }
</style>
