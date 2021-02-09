<template>
  <v-app>
    <NavDrawer></NavDrawer>
    <v-app-bar app color="primary" dark>
      <v-btn flat to="/">Messages</v-btn>
      <v-spacer />
      <v-btn flat to="/users">Users</v-btn>
      <v-spacer />
      <v-btn flat to="/register">Register</v-btn>
      <v-spacer />
      <v-btn flat to="/">Home</v-btn>
      <v-spacer />
      <v-btn flat to="/login">Login</v-btn>
    </v-app-bar>

    <v-main>

      <v-container fluid>
      <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import NavDrawer from "./components/NavDrawer";
// import Users from "./components/Users";

export default {
  name: "App",

  components: {
    NavDrawer,
    // Users,
  },

  data: () => ({
    //
  }),
};
</script>
