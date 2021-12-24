<template>
  <v-card>
<!--    <v-alert type="success" v-if="getIsLoggedIn" v-show="loginSucces">-->
<!--      Logged In Successfully.-->
<!--    </v-alert>-->
    <v-toolbar dark>
      <v-toolbar-title>Settings</v-toolbar-title>
    </v-toolbar>
    <v-container fluid>
      <v-row>
        <v-col cols="4">
          <v-subheader>Change The Budget For Accounting Periods</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
              label="Amount"
              v-model="budgetAdjustment"
              prefix="$"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="4">
          <v-subheader>Suffix for weight</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
              label="Weight"
              value="28.00"
              suffix="lbs"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="4">
          <v-subheader>Suffix for email domain</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
              label="Email address"
              value="example"
              suffix="@gmail.com"
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="4">
          <v-subheader>Suffix for time zone</v-subheader>
        </v-col>
        <v-col cols="8">
          <v-text-field
              label="Label Text"
              value="12:30:00"
              type="time"
              suffix="PST"
          ></v-text-field>
        </v-col>
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
      budgetAdjustment: 666,
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
      this.$store.dispatch("fetchPeriodBudgets", currentYear).then(() => {
        const currentMonth = new Date().getMonth();
        this.displayBudgetValueByMonth(currentMonth);
      });
    },
    displayBudgetValueByMonth(month) {
      const periodBudgets = this.$store.getters.getPeriodBudgets;
      console.log('period budgets');
      console.log(periodBudgets);
      console.log(month);
    },
  },

  computed: {
    ...mapGetters([
      'getPeriodBudgets'
    ]),
  },
};
</script>