<template>
  <div>
    <v-container class="grey lighten-5">
      <v-row no-gutters>
<!--        cols="12" for full width; offset-sm="1" for empty column space-->
        <v-col sm="4" offset-sm="1" v-for="account in getAccounts" :key="account.accountId">
          <v-card color="#385F73" dark>
            <v-card-title class="headline">
              {{account.balance}}
            </v-card-title>

            <v-card-subtitle>{{account.name}}</v-card-subtitle>

<!--            <v-card-actions>-->
<!--              <v-btn text>Listen Now</v-btn>-->
<!--            </v-card-actions>-->
          </v-card>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>
// import axios from "axios";

import {mapGetters} from "vuex";

export default {
  data: () => ({
  }),
  created() {
  },
  computed: {
    ...mapGetters([
      'getAccounts',
    ]),
    // formTitle() {
    //   return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    // },
  },
};
</script>