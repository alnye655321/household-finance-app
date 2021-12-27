<template>
  <v-card>
    <!--    <v-alert type="success" v-if="getIsLoggedIn" v-show="loginSucces">-->
    <!--      Logged In Successfully.-->
    <!--    </v-alert>-->
    <v-toolbar dark>
      <v-toolbar-title>Stock Purchase</v-toolbar-title>
    </v-toolbar>
    <v-container fluid>
      <v-row>
        <v-col>
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
        </v-col>

        <v-col>
          <v-text-field label="Ticker" v-model="selectedItem.ticker" required></v-text-field>
        </v-col>

        <v-col>
          <v-text-field label="Buy Price" v-model="selectedItem.buyPrice" prefix="$" required></v-text-field>
        </v-col>

        <v-col>
          <v-text-field label="Quantity" v-model="selectedItem.quantity" required></v-text-field>
        </v-col>

        <v-col>
          <v-date-picker v-model="datePicker"></v-date-picker>
        </v-col>

      </v-row>

      <v-row>
        <v-btn color="primary" class="mr-4" @click="createStockPurchase">Submit</v-btn>
      </v-row>


    </v-container>
  </v-card>
</template>

<script>
import { mapGetters } from 'vuex';
// import axios from "axios";

export default {
  data() {
    return {
      quantity: 0,
      selectedItem: {},
      datePicker: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    };
  },
  created() {
    this.loadInitData();
  },
  methods: {
    test() {
      console.log('test');
      //fetchPeriodBudgets
    },
    loadInitData() {
      const currentYear = new Date().getFullYear();
      console.log(currentYear);
      this.$store.dispatch("fetchAccounts");
      // this.$store.dispatch("fetchPeriodBudgets", currentYear).then(() => {
      //   const currentMonth = new Date().getMonth();
      //   this.displayBudgetValueByMonth(currentMonth);
      //   this.adjustmentMonth = currentMonth + 1;
      // });
    },
    createStockPurchase() {
      console.log('creating stock purchase');
      console.log(this.selectedItem);
      console.log(this.datePicker);
      this.selectedItem.buyDate = this.datePicker;

      this.$store.dispatch("createStockPurchase", this.selectedItem) //send the account to the server
          .then(() => {
            console.log('done');
          });

    },
  },
  computed: {
    ...mapGetters([
      'getPeriodBudgets',
      'getAccounts'
    ]),
  },
};
</script>