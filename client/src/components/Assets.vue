<template>
  <div>

    <v-row>
      <v-col>
        <AccountPie></AccountPie>
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

  }
};
</script>
