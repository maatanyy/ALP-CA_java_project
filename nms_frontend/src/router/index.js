import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AdPackageList from '../views/AdPackageList.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/adpackages',
      name: 'AdPackageList',
      component: AdPackageList
    },

    {
      path: '/chart',
      name: 'chart',      
      component: () => import('../views/MyChart.vue')
    }
    ,{
      path: '/gender',
      name: 'gender',      
      component: () => import('../views/Gender.vue')
    }
    ,{
      path: '/company',
      name: 'company',      
      component: () => import('../views/Company.vue')
    }
    ,{
      path: '/addcompany',
      name: 'addcompany',      
      component: () => import('../views/AddCompany.vue')
    }
    ,{
      path: '/companylist',
      name: 'companylist',      
      component: () => import('../views/CompanyList.vue')
    }
    ,{
      path: '/addad',
      name: 'addad',      
      component: () => import('../views/AddAd.vue')
    }
    ,{
      path: '/adlist',
      name: 'adlist',      
      component: () => import('../views/AdList.vue')
    }
    ,{
      path: '/addcustomer',
      name: 'addcustomer',      
      component: () => import('../views/AddCustomer.vue')
    }
    ,{
      path: '/customerlist',
      name: 'customerlist',      
      component: () => import('../views/CustomerList.vue')
    }
    ,{
      path: '/watchinghistory',
      name: 'watchinghistory',      
      component: () => import('../views/WatchingHistory.vue')
    }
    ,{
      path: '/watchinghistorylist',
      name: 'watchinghistorylist',      
      component: () => import('../views/WatchingHistoryList.vue')
    }
    ,{
      path: '/updateadpackage',
      name: 'updateadpackage',      
      component: () => import('../views/UpdateAdPackage.vue')
    }

  ]
})

export default router
