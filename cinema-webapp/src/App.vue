<template>
  <v-app>
    <v-navigation-drawer app v-model="sideNav">
      <v-list>
        <v-list-item
          v-for="item in filteredMenuItems"
          :key="item.title"
          :to="item.link"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-title>
            {{ item.title }}
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-nav-icon @click="sideNav = !sideNav"></v-app-bar-nav-icon>
      <v-app-bar-title>Cinema</v-app-bar-title>
      <v-spacer></v-spacer>
      <v-row v-if="username">
        <v-list-item-content  style="text-align: right">
          <v-list-item-title >
            Greetings,
            <span class="font-weight-bold">
              {{ username }}
            </span>
          </v-list-item-title>
          <v-list-item-subtitle>
            <a @click="logout">Logout</a>
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-row>
      <v-btn v-else to="/login" style="text-align: right" text>
        <v-icon>mdi-login</v-icon> Login
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Cookie from '@/common/security';
import { eventBus, EVENT_LOGGED_IN } from '@/common/eventBus';

export default {
  name: 'CinemaApp',
  data() {
    return {
      sideNav: false,
    };
  },
  created() {
    eventBus.$on(EVENT_LOGGED_IN, () => {
      window.location.reload();
    });
  },
  computed: {
    menuItems() {
      return [
        {
          icon: 'mdi-home', title: 'Main page', link: '/', show: true,
        },
        {
          icon: 'mdi-account-plus', title: 'Register', link: '/register-form', show: !this.loggedIn,
        },
        {
          icon: 'mdi-format-list-bulleted', title: 'My reservations', link: '/my-reservations', show: this.loggedIn,
        },
      ];
    },
    filteredMenuItems() {
      return this.menuItems.filter((it) => it.show);
    },
    username() {
      return Cookie.getLogin();
    },
    loggedIn() {
      return !!this.username;
    },
  },
  methods: {
    logout() {
      Cookie.removeLoginCookie();
      this.$router.push({ name: 'Home' });
      window.location.reload();
    },
  },
};
</script>
