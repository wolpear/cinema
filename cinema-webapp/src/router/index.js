import Vue from 'vue';
import VueRouter from 'vue-router';
import Register from '@/components/Register';
import Home from '@/components/Home';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/register-form',
    name: 'Register form',
    component: Register,
  },
];

// eslint-disable-next-line import/prefer-default-export
export const router = new VueRouter({
  routes,
  mode: 'history',
});
