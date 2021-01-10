import Vue from 'vue';
import VueRouter from 'vue-router';
import Register from '@/components/Register';
import Home from '@/components/Home';
import Login from '@/components/Login';
import ProjectionsList from '@/components/MovieProjections';
import MovieReservation from '@/components/MovieReservation';

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
  {
    path: '/login',
    name: 'Login form',
    component: Login,
  },
  {
    path: '/movie-projections/:movieId',
    name: 'Movie projections list',
    component: ProjectionsList,
    props: true,
  },
  {
    path: '/movie-reservation/:projectionId',
    name: 'Movie reservation',
    component: MovieReservation,
    props: true,
  },
];

// eslint-disable-next-line import/prefer-default-export
export const router = new VueRouter({
  routes,
  mode: 'history',
});
