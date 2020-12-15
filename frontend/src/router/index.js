import Vue from 'vue'
import VueRouter from 'vue-router'
import singIn from '../views/signIn.vue'
import signUp from '../views/signUp.vue'
import home from '../views/home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/signIn',
    name: 'signIn',
    component: singIn
  },
  {
    path: '/signUp',
    name: 'signUp',
    component: signUp
  },
  {
    path: '/home',
    name: 'home',
    component: home
  }
]

const router = new VueRouter({
  routes
})

export default router
