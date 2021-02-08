<template>
  <div>
    <v-btn class="mt-12" color="primary" @click="showOverlay = !showOverlay">New Account</v-btn>

    <v-card max-width="375" class="mx-auto" v-for="account in getAccounts" :key="account.accountId">
      <v-img
          src="https://cdn.vuetifyjs.com/images/lists/ali.png"
          height="300px"
          dark
      >
        <v-row class="fill-height">
          <v-card-title>
            <v-btn dark icon>
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>

            <v-spacer></v-spacer>

            <v-btn dark icon class="mr-4">
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

    <!--  Begin Overlay-->
    <v-overlay :absolute="overlayAbsolute" :opacity="overlayOpacity" :value="showOverlay" :z-index="overlayzIndex">
      <v-form ref="form" v-if="selectedItem.hasOwnProperty('name')" lazy-validation>
        <v-text-field v-model="selectedItem.name" label="Name" required> </v-text-field>

        <v-select v-model="selectedItem.budgetType"
                  :hint="`${selectedItem.type}, ${selectedItem.budgetTypeId}`"
                  :items="getBudgetTypes"
                  item-text="type"
                  item-value="budgetTypeId"
                  label="Select"
                  persistent-hint
                  return-object
                  single-line
        ></v-select>

        <v-select v-model="selectedItem.accountingPeriod"
                  :items="getAccountingPeriods"
                  item-text="startDate"
                  item-value="accountingPeriodId"
                  label="Select"
                  return-object
                  single-line
        ></v-select>

        <v-text-field v-model="selectedItem.budgetType.type" label="BudgetType" required></v-text-field>
        <v-text-field v-model="selectedItem.account.name" label="Account" required></v-text-field>
        <v-text-field label="Amount" v-model="selectedItem.amount" prefix="$"></v-text-field>
        <v-text-field disabled label="Created Date" v-model="selectedItem.createdDate"></v-text-field>
        <v-text-field v-model="selectedItem.committed" label="Committed" required></v-text-field>


        <v-btn color="primary" class="mr-4" @click="updateItem">Submit</v-btn>

        <v-btn color="error" class="mr-4" @click="test">Reset Form</v-btn>

        <v-btn color="warning" @click="test">Reset Validation</v-btn>

      </v-form>
      <v-btn color="primary" @click="showOverlay = false">Hide Overlay</v-btn>
    </v-overlay>
    <!--  End Overlay-->

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
      "name": ""
    }, //updated for new/edit item form
    overlayAbsolute: false,
    overlayOpacity: 0.46,
    showOverlay: false,
    overlayzIndex: 5,
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
    this.$store.dispatch("fetchAccounts", this.$store.getters.getUser.userId);
    this.$store.dispatch("fetchAccountTypes");
  },




}

</script>