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
          v-model="form.firstName"
          :counter="20"
          :rules="rules.firstName"
          label="First name"
          autocomplete="off"
          outlined
          required
      ></v-text-field>

      <v-text-field
          v-model="form.lastName"
          :counter="30"
          :rules="rules.lastName"
          label="Last name"
          autocomplete="off"
          outlined
          required
      ></v-text-field>

      <v-text-field
          v-model="form.email"
          :counter="50"
          :rules="rules.email"
          label="E-mail"
          type="email"
          autocomplete="off"
          outlined
          required
      ></v-text-field>

      <v-text-field
          v-model="form.password"
          :counter="50"
          :rules="rules.password"
          label="Password"
          type="password"
          autocomplete="new-password"
          outlined
          required
      ></v-text-field>

      <v-text-field
          v-model="form.repeatPassword"
          :counter="50"
          :rules="[repeatPasswordRule]"
          label="Repeat password"
          type="password"
          autocomplete="off"
          outlined
          required
      ></v-text-field>

      <v-btn
        type="submit"
        :disabled="!valid"
      >Submit</v-btn>
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
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      repeatPassword: '',
    },
    rules: {
      firstName: [
        (v) => !!v || 'First name is required',
        (v) => (v && v.length <= 20) || 'First name must be less than 20 characters',
      ],
      lastName: [
        (v) => !!v || 'Last name is required',
        (v) => (v && v.length <= 30) || 'Last name must be less than 20 characters',
      ],
      email: [
        (v) => !!v || 'E-mail is required',
        (v) => (v && v.length <= 30) || 'E-mail must be less than 50 characters',
      ],
      password: [
        (v) => !!v || 'Password is required',
        (v) => (v && v.length <= 30) || 'Password must be less than 50 characters',
      ],
    },
  }),
  methods: {
    repeatPasswordRule() {
      if (!this.form.repeatPassword) {
        return 'Password needs to be entered again!';
      }
      return this.form.password === this.form.repeatPassword || 'Passwords don\'t match!';
    },
    submitForm() {
      this.error = null;
      this.success = null;
      http.post('/api/v1/clients', {
        firstName: this.form.firstName,
        lastName: this.form.lastName,
        email: this.form.email,
        plaintextPassword: this.form.password,
      }).then((res) => {
        this.success = res.data.message;
      }).catch((err) => {
        this.error = err.response.data.message;
      });
    },
  },
};
</script>

<style scoped>

</style>
