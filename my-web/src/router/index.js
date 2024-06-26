import Vue from 'vue'
import VueRouter from 'vue-router'
/* Layout */
import Layout from '@/layout'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Layout, // 修改为 Layout 组件
    children: [
      {
        path: '',
        component: () => import('../views/User/Question_Generate.vue'),
        name: 'Immunology-Question-Generation-System-Based-on-LLM'
      },
      {
        path: 'user/exam-history',
        component:() => import( '../views/User/Exam_History.vue')
      },
      {
        path: 'user/change-info',
        component:() => import( '../views/User/Change_Info.vue')
      },
      {
        path: 'user/exam-generated',
        component:() => import( '../views/User/Side_Page/Exam_Generated.vue')
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/submit',
    name: 'submit',
    component: () => import('../views/Submit.vue')
  },
]

// 防止连续点击多次路由报错
let routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
