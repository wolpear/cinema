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
      <v-row v-if="username()">
        <v-list-item-content  style="text-align: right">
          <v-list-item-title >
            Greetings,
            <span class="font-weight-bold">
              {{ username() }}
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

export default {
  data() {
    return {
      sideNav: false,
      menuItems: [
        {
          icon: 'mdi-home', title: 'Main page', link: '/', show: true,
        },
        {
          icon: 'mdi-account-plus', title: 'Register', link: '/register-form', show: !this.username(),
        },
      ],
    };
  },
  computed: {
    filteredMenuItems() {
      return this.menuItems.filter((it) => it.show);
    },
  },
  methods: {
    logout() {
      Cookie.removeLoginCookie();
      window.location.reload();
    },
    username() {
      return Cookie.getLogin();
    },
  },
};
</script>
