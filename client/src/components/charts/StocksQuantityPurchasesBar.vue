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
  LegendComponent,
    GridComponent
} from "echarts/components";
import VChart, { THEME_KEY } from "vue-echarts";
import {mapGetters} from "vuex";

use([
  CanvasRenderer,
  BarChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
    GridComponent
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
          data: this.last12Months(),
          inverse: true //flips the axis
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
          text: 'Stock Purchases By Quantity - SPY',
          x: 'center',
          textStyle: {
            fontSize: 24
          }
        },
        color: ['#127ac2']

      }
    };
  },
  created() {
    this.loadInitData();
  },
  methods: {
    loadInitData() {
      this.$store.dispatch("fetchStockPurchases");
    },
    stockPurchasesData() {
      const stockPurchases = this.$store.getters.getStockPurchases;
      console.log('test data!!!');
      console.log(stockPurchases);

      // const totalQuantity = stockPurchases.reduce((total,currentValue) => {
      //   return {quantity: total.quantity += currentValue.quantity};
      // });

      const monthsArray = this.last12Months();
      console.log(monthsArray);
      let stockDataArray = [];

      for (let i = 0; i < monthsArray.length; i++) {
        const monthStockData = stockPurchases.filter(e => e.buyDate.includes(monthsArray[i]));
        stockDataArray.push(monthStockData.length);
      }

      return stockDataArray;
    },
    last12Months() {
      let currentYear = new Date().getFullYear();
      let currentMonth = new Date().getMonth();

      let monthArray = [];
      for (let i = 12; i > 0; i--) {
        const monthValue = currentMonth + 1;
        monthArray.push(currentYear + '-' + String(monthValue).padStart(2, '0'));

        currentMonth--;
        if (currentMonth == -1) {
          currentYear--;
          currentMonth = 11;
        }

      }

      return monthArray;
    },
  },
  computed: {
    ...mapGetters([
      'getStockPurchases',
    ]),
  },
};
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>