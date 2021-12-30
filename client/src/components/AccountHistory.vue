<template>
  <v-card>
    <!--    <v-alert type="success" v-if="getIsLoggedIn" v-show="loginSucces">-->
    <!--      Logged In Successfully.-->
    <!--    </v-alert>-->
    <v-toolbar dark>
      <v-toolbar-title>Account History</v-toolbar-title>
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
          <v-text-field label="Monthly Balance Observation" v-model="selectedItem.balanceObserved" prefix="$" required></v-text-field>
        </v-col>

        <v-col>
          <v-date-picker v-model="datePicker"></v-date-picker>
        </v-col>

      </v-row>

      <v-row>
        <v-btn color="primary" class="mr-4" @click="createAccountHistory">Submit</v-btn>
      </v-row>

      <br><br>
      <v-divider></v-divider>

      <v-simple-table>
        <template v-slot:default>
          <thead>
          <tr>
            <th class="text-left">
              Account
            </th>
            <th class="text-left">
              Balance Observed
            </th>
            <th class="text-left">
              Date Observed
            </th>
          </tr>
          </thead>
          <tbody>
          <tr
              v-for="item in getAccountHistories"
              :key="item.accountHistoryId"
          >
            <td>{{ item.account.name }}</td>
            <td>{{ item.balanceObserved.toFixed(2) }}</td>
            <td>{{ item.dateObserved }}</td>
          </tr>
          </tbody>
        </template>
      </v-simple-table>


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
    //if not authenticated try authenticating from existing token
    const currentUser = this.$store.getters.getUser;

    if (typeof currentUser.name === 'undefined' && typeof localStorage.token !== 'undefined') {
      console.log('Using Existing Token For Login');
      this.$store.dispatch("setUserFromExistingToken", localStorage.token).then(() => {
        this.loadInitData();
      });
    }
    else {
      this.loadInitData();
    }
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
      this.$store.dispatch("fetchAccountHistories");
      // this.$store.dispatch("fetchStockPurchases");
      // this.$store.dispatch("fetchPeriodBudgets", currentYear).then(() => {
      //   const currentMonth = new Date().getMonth();
      //   this.displayBudgetValueByMonth(currentMonth);
      //   this.adjustmentMonth = currentMonth + 1;
      // });
    },
    createAccountHistory() {
      console.log('creating account history');
      console.log(this.selectedItem);
      console.log(this.datePicker);
      this.selectedItem.dateObserved = this.datePicker;

      this.$store.dispatch("createAccountHistory", this.selectedItem) //send the account to the server
          .then(() => {
            this.$store.dispatch("fetchAccountHistories");
            console.log('done');
          });

    },
  },
  computed: {
    ...mapGetters([
      'getPeriodBudgets',
      'getAccountHistories',
      'getAccounts'
    ]),
  },
};
</script>