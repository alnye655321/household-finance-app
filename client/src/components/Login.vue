<template>
  <v-flex sm8 offset-sm2>
    <v-card>
      <v-alert type="success" v-if="getIsLoggedIn">
        I'm a success alert.
      </v-alert>
      <v-toolbar dark>
        <v-toolbar-title>Login</v-toolbar-title>
      </v-toolbar>
      <v-form>
        <v-container>
          <v-layout>
            <v-flex xs12 md4>
              <v-text-field
                v-model="userName"
                label="User Name"
                required
              ></v-text-field>
            </v-flex>
            <v-flex xs12 md4>
              <v-text-field
                v-model="password"
                label="Password"
                required
              ></v-text-field>
            </v-flex>
          </v-layout>
        </v-container>
        <v-btn @click="login">Login</v-btn>
      </v-form>
    </v-card>
  </v-flex>
</template>

<script>
import { mapGetters } from 'vuex';
// import axios from "axios";

export default {
  data() {
    return {
      userName: "",
      password: "",
      loginSucces: false
    };
  },
  methods: {
    login() {
      this.$store.dispatch("login", {
        name: this.userName,
        hashedPassword: this.password,
        email: '',
      });
    },
  },
  computed: {
    ...mapGetters([
        'getIsLoggedIn'
    ])
  },
};
</script>