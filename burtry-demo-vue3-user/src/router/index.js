import { createRouter, createWebHistory } from 'vue-router'
import LayoutView from '../views/layout/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'layout',
      component: LayoutView,
      children: [
        {
          path: '',
          name: 'home',
          component: () => import("@/views/HomeView.vue")
        },
        {
          path: '/search',
          name: 'search',
          component: () => import("@/views/SearchView.vue")
        },
        // 个人中心
        {
          path: '/user/:id',
          name: 'user',
          component: () => import("@/views/UserView.vue")
        },
        {
          path: 'article/:id',
          name: 'article',
          component: () => import("../views/article/DetailArticleView.vue")
        },
        {
          path: 'text',
          name: 'text',
          component: () => import("../views/TextView.vue")
        },
        {
          path: 'textManagement',
          name: 'textManagement',
          component: () => import("../views/TextManagementView.vue")
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import("../views/LoginView.vue")
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import("@/components/NotFoundView.vue")
    }


  ]
})

export default router
