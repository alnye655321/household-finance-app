<template>
  <div>
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

    <v-btn class="mt-12" color="primary" @click="updateFormForItemCreation(); showOverlay = !showOverlay;">New Savings Goal</v-btn>

    <!--  Begin Create/Edit Savings Goal Overlay-->
    <v-overlay :absolute="overlayAbsolute" :opacity="overlayOpacity" :value="showOverlay" :z-index="overlayZIndex">
      <v-form ref="form" v-if="selectedItem.hasOwnProperty('name')" lazy-validation>

        <!--  start alert-->
        <v-alert
            v-model="alert"
            close-text="Close Alert"
            color="deep-purple accent-4"
            dark
            dismissible
        >
          Please complete all form items
        </v-alert>
        <!--  end alert-->

        <v-text-field v-model="selectedItem.name" label="Name" required> </v-text-field>

        <v-select v-model="selectedItem.account"
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


        <v-text-field label="Amount" v-model="selectedItem.amount" prefix="$" required></v-text-field>


        <v-text-field v-model="selectedItem.notes" label="Notes"> </v-text-field>


        <v-text-field disabled label="Created Date" v-model="selectedItem.createdDate"></v-text-field>

        <v-btn color="success" class="mr-4" v-if="createFormActive" @click="createItem">Submit</v-btn>
        <v-btn color="success" class="mr-4" v-if="!createFormActive" @click="test">Submit</v-btn>
        <!--      <v-btn color="error" class="mr-4" @click="test">Reset Form</v-btn>-->
        <!--      <v-btn color="warning" @click="test">Reset Validation</v-btn>-->
        <v-btn color="primary" @click="showOverlay = false">Cancel</v-btn>

      </v-form>

    </v-overlay>
    <!--  End Create/Edit Savings Goal Overlay-->

  </div>

</template>

<script>

import { mapGetters } from 'vuex'
// import axios from "axios";

export default {
  data: () => ({
    createFormActive: false,
    selectedItem: { }, //updated for new/edit item form
    alert: false,
    overlayAbsolute: false,
    overlayOpacity: 0.86,
    showOverlay: false,
    overlayZIndex: 5,
  }),
  computed: {
    ...mapGetters([
      'getAccounts',
      'getUser',
    ]),
  },
  created() {

    this.$store.dispatch("fetchSavingsGoals");

    this.$store.dispatch("fetchAccounts"); //TODO should only be getting savings accounts here
  },
  methods: { //createSavingsGoal
    createItem() {
      const valid = this.$refs.form.validate();

      if (valid
          && typeof this.selectedItem.account.accountId !== 'undefined' && this.selectedItem.account.accountId > -1
      ) {
        console.log('creating item');
        this.prevSelectedAccountingPeriod = this.selectedItem.accountingPeriod;

        this.$store.dispatch("createSavingsGoal", this.selectedItem)
            .then(() => {
              console.log('savings goal created')
              // this.$store.dispatch("fetchBudgetItems", this.$store.getters.getUser.userId) //important that budget items are sent first
              //     .then(() => {
              //       this.$store.dispatch("fetchAccountingPeriods"); //will eventually commit a mutation that arranges budget items into a months array - getBudgetItemsByMonth
              //     });
            });

        this.showOverlay = false;
      }
      else {
        console.log('invalid');
        this.alert = true; //show invalid form alert message
        setTimeout(() => { this.alert = false; }, 3000); //remove alert message after a time period

      }
    },
    updateFormForItemCreation() {
      this.createFormActive = true;
      const today = new Date();

      this.selectedItem = {
        "name": "",
        "user": this.$store.getters.getUser,
        "account": {},
        "notes": "",
        "amount": 0,
        "committed": false,
        "createdDate": today
      };
    },
    test() {
      console.log('test');
    },
  },



}
</script>