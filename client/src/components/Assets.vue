<template>
  <div>
    <v-row>
      <v-col>
        <SamplePie></SamplePie>
      </v-col>
    </v-row>

    <br><br>

    <v-row>
      <v-col>
        <StocksQuantityPurchasesBar></StocksQuantityPurchasesBar>
      </v-col>
    </v-row>
  </div>

</template>

<script>
import SamplePie from "@/components/SamplePie";
import StocksQuantityPurchasesBar from "@/components/charts/StocksQuantityPurchasesBar";

export default {
  name: "HelloWorld",
  components: {
    SamplePie,
    StocksQuantityPurchasesBar,
  },
  data() {
    return {
    };
  }
};
</script>
