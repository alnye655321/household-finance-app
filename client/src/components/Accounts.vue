<template>
  <div>
    <v-container>
      <v-row>
    <v-btn class="mt-12" color="primary" @click="showOverlay = !showOverlay">New Account</v-btn>
      </v-row>
      <br><br>
      <v-layout row justify-space-between class="d-flex flex-row mb-6">
<!--      eslint-disable-next-line vue/no-unused-vars-->
      <div v-for="(account, index) in getAccounts" :key="account.accountId">
        <v-flex class="pa-2">

        <v-card min-width="300" class="mx-auto">

          <v-row>
            <v-col md="1" offset-md="1">
              <v-btn dark icon class="mr-4" color="blue" @click="selectedAccount = account; editAccountOverlay = true;">
                <v-icon>mdi-pencil</v-icon>
              </v-btn>
            </v-col>

            <v-col>
              <v-btn dark icon color="blue">
                <v-icon>mdi-dots-vertical</v-icon>
              </v-btn>
            </v-col>
          </v-row>

          <v-img
              :src="findImage(index)"
              height="100px"
              width="100px"
              dark
          >
          </v-img>


          <v-card-title>{{account.name}}</v-card-title>

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
    },
    overlayAbsolute: false,
    overlayOpacity: 0.86,
    showOverlay: false,
    overlayzIndex: 5,
    editAccountOverlay: false,
    selectedAccount: {},
    imageSources : [
      "icons8-bug-96.png",
      "icons8-bull-96.png",
      "icons8-butterfly-96.png",
      "icons8-dove-96.png",
      "icons8-dragon-96.png",
      "icons8-hatching-chicken-96.png",
      "icons8-hive-96.png",
      "icons8-honeycombs-96.png",
      "icons8-jackalope-96.png",
      "icons8-ladybird-96.png",
      "icons8-pelican-96.png",
      "icons8-penguin-96.png",
      "icons8-swan-96.png",
      "icons8-turkeycock-96.png",
    ]
  }),
  computed: {
    ...mapGetters([
      'getAccounts',
      'getUser',
      'getAccountTypes',
    ]),
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
    findImage(index) { //needed to return an image source from array of paths - using require below
      console.log('hereeee');
      console.log(index);
      console.log(this.imageSources);
      return require(`../assets/${this.imageSources[index]}`) // the module request
      // return require(`../assets/icons8-bug-96.png`) // the module request
    },
    updateAccount() {
      this.$store.dispatch("updateAccount", this.selectedAccount) //send the account to the server
          .then(() => {
            this.$store.dispatch("fetchAccounts"); //get new accounts list after server updates
            this.editAccountOverlay = false; //close the account overlay after submission
          });
    },
    test() {
      console.log('');
    },
  },




}

</script>