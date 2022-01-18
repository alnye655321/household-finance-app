<template>
    <v-card>
      <v-alert type="success" v-if="getIsLoggedIn" v-show="loginSucces">
        Logged In Successfully.
      </v-alert>
      <v-alert type="error"  v-show="loginFailure">
        Log In Failure. Please Check Credentials.
      </v-alert>
      <v-toolbar dark>
        <v-toolbar-title>Login</v-toolbar-title>
      </v-toolbar>
      <v-container>
        <v-row  justify="center">
          <v-col cols="3">
            <v-text-field
                v-model="userName"
                label="User Name"
                required
            ></v-text-field>
          </v-col>
          <v-col cols="3">
            <v-text-field
                v-model="password"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :type="show1 ? 'text' : 'password'"
                name="input-10-1"
                label="Password"
                counter
                required
                @click:append="show1 = !show1"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row justify="center" style="padding-bottom: 50px">
          <v-btn color="primary" @click="login">Login</v-btn>
        </v-row>
      </v-container>
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
      loginSucces: true,
      loginFailure: false,
      show1: false,
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
          this.$store.dispatch("fetchStockPurchases"); //staging for init load of charts
          this.$store.dispatch("fetchAccountHistories");
          this.$store.dispatch("fetchAccounts");

          setTimeout(()=>{
            this.timeoutSuccessMessage()
          },4000);
        }

        //could be expired token, remove token, resubmit try again
        if(res.status === 403) {
          console.log('trying to reset expired token');
          localStorage.removeItem('token');
          this.$store.dispatch("login", {
            name: this.userName,
            hashedPassword: this.password,
            email: '',
          });
        }
      }).catch((e) => {
        console.log(e);
        this.loginFailure = true;

        setTimeout(()=>{
          this.loginFailure = false;
        },4000);
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