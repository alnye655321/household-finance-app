<template>
  <div>
    <v-container>

      <v-layout row justify-space-between class="d-flex flex-row mb-6">
        <div v-for="(account, index) in getAccounts" :key="account.accountId">
          <v-flex class="pa-2">
            <v-card :color="cardColors[index] || '#387575'" dark max-width="200">
              <v-card-title class="headline">
                ${{account.balance.toFixed(2)}}
              </v-card-title>

              <v-card-subtitle>{{account.name}}</v-card-subtitle>

              <!--            <v-card-actions>-->
              <!--              <v-btn text>Listen Now</v-btn>-->
              <!--            </v-card-actions>-->
            </v-card>
          </v-flex>

        </div>
      </v-layout>

      <!--      <v-row no-gutters>-->
      <!--        <v-col sm="4" offset-sm="1" v-for="(account, index) in getAccounts" :key="account.accountId">-->
      <!--        </v-col>-->
      <!--      </v-row>-->
    </v-container>

  </div>
</template>

<script>
// import axios from "axios";

import {mapGetters} from "vuex";

export default {
  data: () => ({
    cardColors: [
      "#385F73",
      "#753838",
      "#387566",
      "#573875",
      "#753866",
    ],
    year : 2021,
    years: [
      2021,
      2022
    ],
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