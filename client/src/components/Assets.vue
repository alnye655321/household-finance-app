<template>
  <div>

    <v-row>
      <v-col cols="10">
        <AccountPie></AccountPie>
      </v-col>
      <v-col cols="2">
        <v-card :color="'#387575'" dark max-width="200">
          <v-card-title class="headline">
            ${{calculateAccountsTotal()}}
          </v-card-title>
          <v-card-subtitle>Accounts Total</v-card-subtitle>
        </v-card>
      </v-col>
    </v-row>

    <br><br>

    <v-row>
      <v-col>
        <InvestmentAccountGrowthLine></InvestmentAccountGrowthLine>
      </v-col>
    </v-row>

    <br><br>

    <v-row>
      <v-col>
        <StocksQuantityPurchasesBar></StocksQuantityPurchasesBar>
      </v-col>
    </v-row>

    <br><br>

  </div>

</template>

<script>
import StocksQuantityPurchasesBar from "@/components/charts/StocksQuantityPurchasesBar";
import InvestmentAccountGrowthLine from "@/components/charts/InvestmentAccountGrowthLine";
import AccountPie from "@/components/charts/AccountPie";

export default {
  name: "HelloWorld",
  components: {
    StocksQuantityPurchasesBar,
    InvestmentAccountGrowthLine,
    AccountPie,
  },
  data() {
    return {
    };
  },
  created() {
    //if not authenticated try authenticating from existing token
    const currentUser = this.$store.getters.getUser;

    if (typeof currentUser.name === 'undefined' && typeof localStorage.token !== 'undefined') {
      console.log('Using Existing Token For Login');
      this.$store.dispatch("setUserFromExistingToken", localStorage.token);
    }

  },
  methods: {
    calculateAccountsTotal() {
      const accounts = this.$store.getters.getAccounts;
      const total = accounts.reduce((a, b) => ({balance: a.balance + b.balance}));
      return this.commaSeparateNumber(total.balance.toFixed(0));
    },
    commaSeparateNumber(val) {
      while (/(\d+)(\d{3})/.test(val.toString())){
        val = val.toString().replace(/(\d+)(\d{3})/, '$1'+','+'$2');
      }
      return val;
    },
  },
};
</script>
