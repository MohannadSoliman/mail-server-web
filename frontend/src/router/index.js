import Vue from 'vue'
import VueRouter from 'vue-router'
import singIn from '../views/signIn.vue'
import signUp from '../views/signUp.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'signIn',
    component: singIn
  },
  {
    path: '/signUp',
    name: 'signUp',
    component: signUp
  },
]

const router = new VueRouter({
  routes
})

export default router
