<template>
  <v-card>
<!--    <v-alert type="success" v-if="getIsLoggedIn" v-show="loginSucces">-->
<!--      Logged In Successfully.-->
<!--    </v-alert>-->
    <v-toolbar dark>
      <v-toolbar-title>Settings</v-toolbar-title>
    </v-toolbar>
    <v-container fluid>

<!--      <v-row>-->
<!--        <v-col cols="4">-->
<!--          <v-subheader>Change The Budget For Accounting Periods (Starting At Month #)</v-subheader>-->
<!--        </v-col>-->
<!--        <v-col cols="4">-->
<!--          <v-text-field-->
<!--              label="Amount"-->
<!--              v-model="budgetAdjustment"-->
<!--              prefix="$"-->
<!--              @click="showBudgetAdjustmentControls = !showBudgetAdjustmentControls"-->
<!--          ></v-text-field>-->
<!--        </v-col>-->
<!--        <v-col cols="2">-->
<!--          <v-select-->
<!--              v-model="adjustmentMonth"-->
<!--              :items="adjustmentMonths"-->
<!--              menu-props="auto"-->
<!--              label="Select Starting Month"-->
<!--              hide-details-->
<!--              prepend-icon="mdi-calendar"-->
<!--              single-line-->
<!--          ></v-select>-->
<!--        </v-col>-->
<!--        <v-col cols="2">-->
<!--          <v-icon color="green" @click="submitBudgetAdjustment" v-if="showBudgetAdjustmentControls">mdi-check-circle</v-icon>-->
<!--          <v-icon color="red" @click="showBudgetAdjustmentControls = false" style="padding-left: 10px" v-if="showBudgetAdjustmentControls">mdi-cancel</v-icon>-->
<!--        </v-col>-->
<!--      </v-row>-->

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
      // const currentYear = new Date().getFullYear();
      // const currentMonth = new Date().getMonth();
      // this.displayBudgetValueByMonth(currentMonth);
      // this.adjustmentMonth = currentMonth + 1;
    },
  },

  computed: {
    ...mapGetters([
      'getAccounts'
    ]),
  },
};
</script>