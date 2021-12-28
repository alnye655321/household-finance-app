<template>
  <v-chart class="chart" :option="option" />
</template>

<script>
import { use } from "echarts/core";
import { CanvasRenderer } from "echarts/renderers";
import { BarChart } from "echarts/charts";
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from "echarts/components";
import VChart, { THEME_KEY } from "vue-echarts";

use([
  CanvasRenderer,
  BarChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent
]);

export default {
  name: "HelloWorld",
  components: {
    VChart
  },
  provide: {
    [THEME_KEY]: "dark"
  },
  data() {
    return {
      option: {
        xAxis: {
          data: ['Q1', 'Q2', 'Q3', 'Q4']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            type: 'bar',
            data: this.stockPurchasesData()
          }
        ],
        title: {
          text: 'Quarterly Sales Results',
          x: 'center',
          textStyle: {
            fontSize: 24
          }
        },
        color: ['#127ac2']

      }
    };
  },
  methods: {
    stockPurchasesData() {
      const stockPurchases = this.$store.getters.getStockPurchases;
      console.log('test data!!!');
      console.log(stockPurchases);
      return [30, 75, 24, 92];
    },
    last12Months() {
      const stockPurchases = this.$store.getters.getStockPurchases;
      console.log(stockPurchases);

    },
  },
};
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>