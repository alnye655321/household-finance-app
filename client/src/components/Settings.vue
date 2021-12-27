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
          <v-subheader>Change The Budget For Accounting Periods (Starting At Month #)</v-subheader>
        </v-col>
        <v-col cols="4">
          <v-text-field
              label="Amount"
              v-model="budgetAdjustment"
              prefix="$"
              @click="showBudgetAdjustmentControls = !showBudgetAdjustmentControls"
          ></v-text-field>
        </v-col>
        <v-col cols="2">
          <v-select
              v-model="adjustmentMonth"
              :items="adjustmentMonths"
              menu-props="auto"
              label="Select Starting Month"
              hide-details
              prepend-icon="mdi-calendar"
              single-line
          ></v-select>
        </v-col>
        <v-col cols="2">
          <v-icon color="green" @click="submitBudgetAdjustment" v-if="showBudgetAdjustmentControls">mdi-check-circle</v-icon>
          <v-icon color="red" @click="showBudgetAdjustmentControls = false" style="padding-left: 10px" v-if="showBudgetAdjustmentControls">mdi-cancel</v-icon>
        </v-col>
      </v-row>

<!--      <v-row>-->
<!--        <v-col cols="4">-->
<!--          <v-subheader>Suffix for weight</v-subheader>-->
<!--        </v-col>-->
<!--        <v-col cols="8">-->
<!--          <v-text-field-->
<!--              label="Weight"-->
<!--              value="28.00"-->
<!--              suffix="lbs"-->
<!--          ></v-text-field>-->
<!--        </v-col>-->
<!--      </v-row>-->

<!--      <v-row>-->
<!--        <v-col cols="4">-->
<!--          <v-subheader>Suffix for email domain</v-subheader>-->
<!--        </v-col>-->
<!--        <v-col cols="8">-->
<!--          <v-text-field-->
<!--              label="Email address"-->
<!--              value="example"-->
<!--              suffix="@gmail.com"-->
<!--          ></v-text-field>-->
<!--        </v-col>-->
<!--      </v-row>-->

<!--      <v-row>-->
<!--        <v-col cols="4">-->
<!--          <v-subheader>Suffix for time zone</v-subheader>-->
<!--        </v-col>-->
<!--        <v-col cols="8">-->
<!--          <v-text-field-->
<!--              label="Label Text"-->
<!--              value="12:30:00"-->
<!--              type="time"-->
<!--              suffix="PST"-->
<!--          ></v-text-field>-->
<!--        </v-col>-->
<!--      </v-row>-->

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
      showBudgetAdjustmentControls: false,
      adjustmentMonths: [1, 2, 3, 4, 5, 6 ,7, 8, 9, 10, 11, 12],
      adjustmentMonth: 1
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
        this.adjustmentMonth = currentMonth + 1;
      });
    },
    displayBudgetValueByMonth(month) {
      const periodBudgets = this.$store.getters.getPeriodBudgets;
      console.log('period budgets');
      console.log(periodBudgets); //2021-05-15
                                  //0123456
      console.log(month);
      const currentMonthBudgets = periodBudgets.filter(e => {
        console.log(e);
        const monthNumber = parseInt(e.accountingPeriod.startDate.substr(5, 2));
        return month === (monthNumber - 1);
      });

      console.log("currentMonthBudgets");
      console.log(currentMonthBudgets);
      if (currentMonthBudgets.length > 0) {
        this.budgetAdjustment = currentMonthBudgets[0].amount;
      }
    },
    submitBudgetAdjustment() {
      console.log("submitting budget adjustment");
      const budgetAdjustmentPayload = {
        amount: this.budgetAdjustment,
        fromMonth: this.adjustmentMonth - 1
      };
      this.$store.dispatch("periodBudgetsAdjustment", budgetAdjustmentPayload);
    },
  },

  computed: {
    ...mapGetters([
      'getPeriodBudgets'
    ]),
  },
};
</script>