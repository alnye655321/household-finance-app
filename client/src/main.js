import Vue from 'vue';
import VueRouter from 'vue-router';
import App from './App.vue';
import vuetify from './plugins/vuetify';
import store from './store.js';
import ECharts from 'vue-echarts';

import Messages from "./components/Messages";
import Users from "./components/Users";
import Register from "./components/Register";
import Login from "./components/Login";
import BudgetItems from "./components/BudgetItems";
import Accounts from "./components/Accounts";
import Dashboard from "./components/Dashboard";
import Savings from "./components/Savings";
import Settings from "./components/Settings";
import Assets from "./components/Assets";
import StockPurchase from "./components/StockPurchase";
import AccountHistory from "./components/AccountHistory";

Vue.use(VueRouter)
Vue.config.productionTip = false

const routes = [
  {path: "/", component:Login},
  {path: "/messages", component:Messages},
  {path: "/users", component:Users},
  {path: "/register", component:Register},
  {path: "/login", component:Login},
  {path: "/budgetitems", component:BudgetItems},
  {path: "/savings", component:Savings},
  {path: "/accounts", component:Accounts},
  {path: "/settings", component:Settings},
  {path: "/assets", component:Assets},
  {path: "/stockpurchase", component:StockPurchase},
  {path: "/accounthistory", component:AccountHistory},
  {path: "/dashboard", component:Dashboard},
]

const router = new VueRouter({routes, mode: 'history'}); //history mode gets rid of '#' in page url

new Vue({
  vuetify,
  router,
  "v-chart": ECharts,
  store,
  render: h => h(App)
}).$mount('#app')
