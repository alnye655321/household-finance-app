<template>
    <v-card>
      <v-alert type="success" v-if="getIsLoggedIn" v-show="loginSucces">
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
      loginSucces: true
    };
  },
  methods: {
    login() {
      this.$store.dispatch("login", {
        name: this.userName,
        hashedPassword: this.password,
        email: '',
      }).then(res => {
        console.log(res);
        console.log('res' + res.toString());
        console.log('status ' + res.status);

        if (typeof res.data.token !== 'undefined' && res.data.token !== "") {
          console.log("have token");
          this.$store.dispatch("setUsers"); //TODO replace with backend call to find linked users - this is used in budget items display to switch user context for viewing budget items by user

          setTimeout(()=>{
            this.timeoutSuccessMessage()
          },4000);
        }

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
    timeoutSuccessMessage() {
      this.loginSucces = false;
    },
  },
  computed: {
    ...mapGetters([
        'getIsLoggedIn'
    ])
  },
};
</script>