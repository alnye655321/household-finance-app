import Vue from 'vue'
import VueRouter from 'vue-router';
import App from './App.vue'
import vuetify from './plugins/vuetify';
import store from './store.js'

import Messages from "./components/Messages";
import Users from "./components/Users";
import Register from "./components/Register";
import Login from "./components/Login";
import BudgetItems from "./components/BudgetItems";

Vue.use(VueRouter)
Vue.config.productionTip = false

const routes = [
  {path: "/", component:Messages},
  {path: "/users", component:Users},
  {path: "/register", component:Register},
  {path: "/login", component:Login},
  {path: "/budgetitems", component:BudgetItems},
]

const router = new VueRouter({routes, mode: 'history'}); //history mode gets rid of '#' in page url

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
