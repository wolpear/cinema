<template>
  <div>
    <v-alert
        v-if="this.success"
        type="success"
        border="left"
        text
        dense
    >
      {{ this.success }}
    </v-alert>
    <v-alert
        v-if="this.error"
        type="error"
        border="left"
        text
        dense
    >
      {{ this.error }}
    </v-alert>
    <v-form
        ref="form"
        v-model="valid"
        @submit.prevent="submitForm"
    >
      <v-text-field
          v-model="form.email"
          label="E-mail"
          type="email"
          autocomplete="email"
          outlined
          required
      ></v-text-field>

      <v-text-field
          v-model="form.password"
          label="Password"
          type="password"
          autocomplete="current-password"
          outlined
          required
      ></v-text-field>

      <v-btn
        type="submit"
        :disabled="!valid"
      >Login</v-btn>
    </v-form>
  </div>
</template>

<script>

import http from '@/common/http';

export default {
  name: 'Register',
  data: () => ({
    error: null,
    success: null,
    valid: false,
    form: {
      email: '',
      password: '',
    },
  }),
  methods: {
    submitForm() {
      this.error = null;
      this.success = null;
      http.post('/login', {
        email: this.form.email,
        password: this.form.password,
      }).then(() => {
        this.$router.push({ name: 'Home' });
      }).catch((err) => {
        if (err.response.status === 403) {
          this.error = 'Bad e-mail or password!';
        } else {
          this.error = 'Unknown error occurred';
        }
      });
    },
  },
};
</script>

<style scoped>

</style>
