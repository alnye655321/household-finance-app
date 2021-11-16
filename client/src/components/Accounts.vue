<template>
  <div>
    <v-container>
      <v-row>
    <v-btn class="mt-12" color="primary" @click="showOverlay = !showOverlay">New Account</v-btn>
      </v-row>
      <v-layout row justify-space-between class="d-flex flex-row mb-6">
      <div v-for="account in getAccounts" :key="account.accountId">
        <v-flex class="pa-2">
        <v-card min-width="275" class="mx-auto">
          <v-img
              src="../assets/tiger_bow.jpg"
              height="300px"
              dark
          >
            <v-row class="fill-height">
              <v-card-title>
                <v-btn dark icon>
                  <v-icon>mdi-chevron-left</v-icon>
                </v-btn>

                <v-spacer></v-spacer>

                <v-btn dark icon class="mr-4" @click="selectedAccount = account; editAccountOverlay = true;">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>

                <v-btn dark icon>
                  <v-icon>mdi-dots-vertical</v-icon>
                </v-btn>
              </v-card-title>

              <v-spacer></v-spacer>

              <v-card-title class="white--text pl-12 pt-12">
                <div class="display-1 pl-12 pt-12">
                  {{account.name}}
                </div>
              </v-card-title>
            </v-row>
          </v-img>

          <v-list two-line>
            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-currency-usd</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title>{{account.balance}}</v-list-item-title>
                <v-list-item-subtitle>Balance</v-list-item-subtitle>
              </v-list-item-content>
              <!--        <v-list-item-icon>-->
              <!--          <v-icon>mdi-message-text</v-icon>-->
              <!--        </v-list-item-icon>-->
            </v-list-item>

            <v-divider inset></v-divider>

            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-calendar-range</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title>{{account.dateOpened}}</v-list-item-title>
                <v-list-item-subtitle>Date Opened</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-divider inset></v-divider>

            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-cash-multiple</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title>{{account.interestRate}}</v-list-item-title>
                <v-list-item-subtitle>Interest Rate</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-divider inset></v-divider>

            <v-list-item>
              <v-list-item-icon>
                <v-icon color="indigo">mdi-cash-multiple</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title>{{account.accountType.accountType}}</v-list-item-title>
                <v-list-item-subtitle>Account Type</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

            <v-divider inset v-if="account.dateClosed"></v-divider>

            <v-list-item v-if="account.dateClosed">
              <v-list-item-icon>
                <v-icon color="indigo">mdi-cash-multiple</v-icon>
              </v-list-item-icon>

              <v-list-item-content>
                <v-list-item-title>{{account.dateClosed}}</v-list-item-title>
                <v-list-item-subtitle>Date Closed</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>

          </v-list>

        </v-card>
        </v-flex>


<!--        end accounts card display-->
      </div>
      </v-layout>

    <!--  Begin Add Account Overlay-->
    <v-overlay :absolute="overlayAbsolute" :opacity="overlayOpacity" :value="showOverlay" :z-index="overlayzIndex">
      <v-form ref="form" v-if="selectedItem.hasOwnProperty('balance')" lazy-validation>

        <v-text-field v-model="selectedItem.name" label="Name" required> </v-text-field>

        <v-select v-model="selectedItem.accountType"
                  :items="getAccountTypes"
                  item-text="accountType"
                  item-value="accountTypeId"
                  label="Select"
                  return-object
                  single-line
        ></v-select>

        <v-text-field label="Interest Rate" v-model="selectedItem.interestRate" prefix="%"></v-text-field>

        <v-row>
          <v-btn color="primary" class="mr-4" @click="createAccount">Submit</v-btn>

          <v-btn color="warning" class="mr-4" @click="test">Reset Form</v-btn>

          <v-btn color="error" @click="showOverlay = false">Close</v-btn>
        </v-row>
      </v-form>
    </v-overlay>
    <!--  End Add Account Overlay-->

      <!--  Begin Edit Account Overlay-->
      <v-overlay :absolute="overlayAbsolute" :opacity="overlayOpacity" :value="editAccountOverlay" :z-index="overlayzIndex">
        <v-form ref="form" v-if="selectedAccount.hasOwnProperty('balance')" lazy-validation>

          <v-text-field v-model="selectedAccount.balance" label="Balance" required> </v-text-field>

          <v-text-field label="Interest Rate" v-model="selectedAccount.interestRate" prefix="%"></v-text-field>

          <v-row>
            <v-btn color="primary" class="mr-4" @click="updateAccount">Submit</v-btn>

            <v-btn color="warning" class="mr-4" @click="test">Reset Form</v-btn>

            <v-btn color="error" @click="editAccountOverlay = false">Close</v-btn>
          </v-row>
        </v-form>
      </v-overlay>
      <!--  End Add Account Overlay-->

    </v-container>
  </div>
</template>

<script>

import { mapGetters } from 'vuex'

export default {
  data: () => ({
    selectedItem: {
      "linkedUsers": [],
      "accountType": { },
      "balance": 0.0,
      "interestRate": 0.0,
      "dateOpened": "2021-02-08",
      "dateClosed": null,
      "name": "",
      "imageSources" : [
          "../assets/hopper_ball.jpg",
        "../assets/tiger_bow.jpg",
        "../assets/hopper_ball.jpg",
        "../assets/tiger_bow.jpg",
        "../assets/hopper_ball.jpg",
      ]
    },
    overlayAbsolute: false,
    overlayOpacity: 0.86,
    showOverlay: false,
    overlayzIndex: 5,
    editAccountOverlay: false,
    selectedAccount: {}
  }),
  computed: {
    ...mapGetters([
      'getAccounts',
      'getUser',
      'getAccountTypes',
    ]),
    // formTitle() {
    //   return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
    // },
  },
  created() {
    this.$store.dispatch("fetchAccounts");
    this.$store.dispatch("fetchAccountTypes");
  },
  methods: {
    createAccount() {
      this.showOverlay = false;

      this.$store.dispatch("createAccount", this.selectedItem) //send the account to the server
      .then(() => {
        this.$store.dispatch("fetchAccounts"); //get new accounts list after server updates
      });

      //reset add item object
      this.selectedItem = {
        "linkedUsers": [],
        "accountType": { },
        "balance": 0.0,
        "interestRate": 0.0,
        "dateOpened": "2021-02-08",
        "dateClosed": null,
        "name": ""
      };

    },
    updateAccount() {
      this.$store.dispatch("updateAccount", this.selectedAccount) //send the account to the server
          .then(() => {
            this.$store.dispatch("fetchAccounts"); //get new accounts list after server updates
          });
    },
    test() {
      console.log('');
    },
  },




}

</script>