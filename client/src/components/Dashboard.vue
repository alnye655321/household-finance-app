<template>
  <v-card
      class="mx-auto"
      max-width="400"
  >
    <v-img
        class="white--text align-end"
        height="200px"
        src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
    >
      <v-card-title>Top 10 Australian beaches</v-card-title>
    </v-img>

    <v-card-subtitle class="pb-0">
      Number 10
    </v-card-subtitle>

    <v-card-text class="text--primary">
      <div>Whitehaven Beach</div>

      <div>Whitsunday Island, Whitsunday Islands</div>
    </v-card-text>

    <v-card-actions>
      <v-btn
          color="orange"
          text
      >
        Share
      </v-btn>

      <v-btn
          color="orange"
          text
      >
        Explore
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>

import axios from "axios";

export default {
  data: () => ({
  }),
  computed: {
  },
  created() {
    //TODO should check for a timed out token and redirect to login
    if (typeof this.$store.state.user.name === 'undefined' && localStorage.getItem('token')) {
      axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');
      this.$store.dispatch("getUserByToken");
    }

  },
  methods: {
  },



}
</script>