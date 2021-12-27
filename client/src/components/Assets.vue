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
        <Stocks></Stocks>
      </v-col>
    </v-row>
  </div>

</template>

<script>
import SamplePie from "@/components/SamplePie";
import Stocks from "@/components/Stocks";

export default {
  name: "HelloWorld",
  components: {
    SamplePie,
    Stocks,
  },
  data() {
    return {
    };
  }
};
</script>
