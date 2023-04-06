<template>
  <div>
    <div v-if="menuType === 'left'" class="sidebar-container" :class="{'has-logo':showLogo}">
      <logo v-if="showLogo" :collapse="isCollapse"/>
      <el-scrollbar wrap-class="scrollbar-wrapper">
        <el-menu
          :default-active="activeMenu"
          :collapse="isCollapse"
          :background-color="variables.menuBg"
          :text-color="variables.menuText"
          :unique-opened="true"
          :active-text-color="variables.menuActiveText"
          :collapse-transition="false"
          mode="vertical"
        >
          <sidebar-item v-for="route in fullRoutes" :key="route.path" :item="route" :base-path="route.path"/>
        </el-menu>
      </el-scrollbar>
    </div>
    <div v-if="menuType === 'top'" class="sidebar-container top-sidebar">
      <logo v-if="showLogo" :collapse="isCollapse"/>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import Logo from './Logo'
import SidebarItem from './SidebarItem'
import variables from '@/styles/variables.scss'

export default {
  components: {SidebarItem, Logo},
  computed: {
    ...mapGetters([
      'fullRoutes',
      'sidebar'
    ]),
    activeMenu() {
      const route = this.$route
      const {meta, path} = route
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu
      }
      return path
    },
    menuType() {
      localStorage.getItem("menuType") !== null ? this.$store.state.settings.menuType = localStorage.getItem("menuType") : ""
      return this.$store.state.settings.menuType
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo
    },
    variables() {
      return variables
    },
    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
<style>
#app .sidebar-container {
  -webkit-transition: width .28s;
  transition: width .28s;
  width: 200px !important;
  background-color: #304156;
  height: 100%;
  position: fixed;
  font-size: 0;
  top: 0;
  bottom: 0;
  left: 0;
  z-index: 1001;
  overflow: hidden;
}

#app .sidebar-container .el-submenu .el-menu-item, #app .sidebar-container .nest-menu .el-submenu > .el-submenu__title {
  background-color: #1f2d3d !important;
}

#app .sidebar-container .el-menu {
  background-color: #1f2d3d !important;
}

.top-sidebar {
  height: 50px!important;
}

.top-sidebar > div {
  background: white!important;
}

.top-sidebar > div h1 {
  color: #304156!important;
}
</style>
