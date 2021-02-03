<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <v-btn flat to="/">Messages</v-btn>
      <v-spacer />
      <v-btn flat to="/users">Users</v-btn>
      <v-spacer />
      <v-btn flat to="/register">Register</v-btn>
      <v-spacer />
      <v-btn flat to="/login">Login</v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
// import Messages from "./components/Messages";
// import Users from "./components/Users";

export default {
  name: "App",

  components: {
    // Messages,
    // Users,
  },

  data: () => ({
    //
  }),
};
</script>
