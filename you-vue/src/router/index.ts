import {createRouter, createWebHistory} from "vue-router";


const routes = [
    {
        path: '/',
        name: '首页',
        component: () => import('../views/dashboard/index.vue')
    },
    {
        path: '/login',
        component: () => import('@/views/login/index.vue')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;