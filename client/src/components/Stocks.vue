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
          data: this.last12Months()
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

      // return [30, 75, 24, 92];
    },
    last12Months() {
      const stockPurchases = this.$store.getters.getStockPurchases;
      console.log(stockPurchases);

      let currentYear = new Date().getFullYear();
      let currentMonth = new Date().getMonth() + 1;

      let monthArray = [];
      for (let i = 12; i > 0; i--) {
        currentMonth--;
        if (currentMonth == -1) {
          currentYear--;
          currentMonth = 11;
        }
        const monthValue = currentMonth + 1;

        monthArray.push(currentYear + '-' + String(monthValue).padStart(2, '0'));
      }

      return monthArray;

      // const filteredYears = stockPurchases.filter(e => e.buyDate.includes(currentYear));
      // console.log('filter year');
      // console.log(filteredYears);
      // console.log(monthArray);
      //
      // return ['Q1', 'Q2', 'Q3', 'Q4'];

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