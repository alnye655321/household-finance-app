<template>
    <v-card>
      <v-alert type="success" v-if="getIsLoggedIn">
        Logged In Successfully.
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
      }).then(res => {
        console.log('res' + res);
        console.log('status ' + res.status);

        //could be expired token, remove token, resubmit try again
        if(res.status === 403) {
          localStorage.removeItem('token');
          this.$store.dispatch("login", {
            name: this.userName,
            hashedPassword: this.password,
            email: '',
          });
        }
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