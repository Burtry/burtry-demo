import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      redirect: '/article',
      children: [
        {
          path: '/article',
          name: 'article',
          component: () => import("../views/components/ArticleView.vue")
        },
        {
          path: '/user',
          name: 'user',
          component: () => import("../views/components/UserView.vue")
        },
        {
          path: '/comment',
          name: 'comment',
          component: () => import("../views/components/CommentView.vue")
        },
        {
          path: '/sensitive',
          name: 'sensitive',
          component: () => import("../views/components/SensitiveView.vue")
        },
        {
          path: '/channel',
          name: 'channel',
          component: () => import("../views/components/ChannelView.vue")
        }

      ]
    },

    {
      path: '/login',
      name: 'login',
      component: () => import("../views/LoginView.vue")
    }
  ]
})

export default router
