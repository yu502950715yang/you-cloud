<template>
  <transition-group name="fade">
    <inner-link
        v-for="(item, index) in tagsViewStore.iframeViews"
        :key="item.path"
        :iframeId="'iframe' + index"
        v-show="route.path === item.path"
        :src="item.meta.link"
    ></inner-link>
  </transition-group>
</template>

<script setup>
import InnerLink from "../InnerLink/index"
import useTagsViewStore from '@/store/modules/tagsView'
import {useRoute} from "vue-router";

const route = useRoute();
const tagsViewStore = useTagsViewStore()
</script>

<style lang="scss">
.fade-move, /* 对移动中的元素应用的过渡 */
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateX(30px);
}

/* 确保将离开的元素从布局流中删除
  以便能够正确地计算移动的动画。 */
.fade-leave-active {
  position: absolute;
}
</style>