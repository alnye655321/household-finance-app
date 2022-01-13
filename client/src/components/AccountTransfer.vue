<template>
  <div>
    <v-container>
      <v-row align="center" justify="center">
        <v-col md="4">
          <v-select v-model="fromAccount"
                    hint="Account"
                    :items="getAccounts"
                    item-text="name"
                    item-value="accountId"
                    label="Select Account"
                    persistent-hint
                    return-object
                    single-line
                    required
          ></v-select>
        </v-col>
        <v-col md="2">
          <v-icon x-large color="blue">mdi-arrow-right-bold-box</v-icon>
        </v-col>
        <v-col md="4">
          <v-select v-model="toAccount"
                    hint="Account"
                    :items="getAccounts"
                    item-text="name"
                    item-value="accountId"
                    label="Select Account"
                    persistent-hint
                    return-object
                    single-line
                    required
          ></v-select>
        </v-col>
        <v-col md="2">
          <v-icon x-large color="green">mdi-swap-horizontal-bold</v-icon>
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
    year : 2021,
    years: [
      2021,
      2022
    ],
    fromAccount: {},
    toAccount: {},
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