<template>
  <div>
    <v-container>
      {{parentAccountingPeriod}}
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
                  @click="createAccountTransfer"
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
      user: {},
      budgetType: {
        budgetTypeId: 3,
        type: "Account Transfer"
      },
      account: {},
      fromAccount: {},
      accountingPeriod: {},
      createdDate: "",
      amount: 0,
    },
  }),
  props: {
    parentAccountingPeriod: Object,
  },
  created() {
    this.toAccount = {};
    this.fromAccount = {};
    console.log(this.parentAccountingPeriod);
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
  methods: {
    createAccountTransfer() {
      console.log("creating account transfer");
      this.transferBudgetItem.user = this.$store.getters.getUser;
      this.transferBudgetItem.name = "From " + this.fromAccount.name;
      this.transferBudgetItem.accountingPeriod = this.parentAccountingPeriod;
      this.transferBudgetItem.account = this.toAccount;
      this.transferBudgetItem.fromAccount = this.fromAccount;
      this.transferBudgetItem.createdDate = new Date();
      this.transferBudgetItem.amount = this.transferAmount;

      this.$store.dispatch("createBudgetItem", this.transferBudgetItem)
          .then(() => {
            console.log('loaded');
          });

    },
  },
};
</script>