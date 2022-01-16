<template>
  <div>
    <v-container>
      {{parentData}}
      <v-row justify="center">
        <v-col cols="4">
          <v-select v-model="fromAccount"
                    hint="Account"
                    :items="getAccounts"
                    item-text="name"
                    item-value="accountId"
                    label="Transfer From"
                    persistent-hint
                    return-object
                    single-line
                    required
          ></v-select>
        </v-col>
        <v-col cols="1">
          <v-icon x-large color="blue">mdi-arrow-right-bold-box</v-icon>
        </v-col>
        <v-col cols="4">
          <v-select v-model="toAccount"
                    hint="Account"
                    :items="getAccounts"
                    item-text="name"
                    item-value="accountId"
                    label="Transfer To"
                    persistent-hint
                    return-object
                    single-line
                    required
          ></v-select>
        </v-col>
        <v-col cols="2">
          <v-text-field label="Transfer Amount" v-model="transferAmount" prefix="$"></v-text-field>
        </v-col>
        <v-col cols="1">
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-icon
                  x-large
                  color="green"
                  dark
                  v-bind="attrs"
                  v-on="on"
              >
                mdi-swap-horizontal-bold
              </v-icon>
            </template>
            <span>Submit Account Transfer</span>
          </v-tooltip>
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
    transferAmount: 0,
    transferBudgetItem: {
      name: "",
      user: this.$store.getters.getUser,
      budgetType: {
        budgetTypeId: 3,
        type: "Account Transfer"
      },
      account: {},
      savingsGoal: {},
      accountingPeriod: {},
      createdDate: "",
    },
  }),
  props: {
    parentData: Object,
  },
  created() {
    this.toAccount = {};
    this.fromAccount = {};
    console.log('submit prop');
    console.log(this.parentData);
  },
  computed: {
    ...mapGetters([
      'getAccounts',
      'getUser',
    ]),
    // formTitle() {
    //   return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    // },
  },
};
</script>