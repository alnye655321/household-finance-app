<template>
  <v-chart class="chart" :option="option" />
</template>

<script>
import { use } from "echarts/core";
import { CanvasRenderer } from "echarts/renderers";
import { LineChart } from "echarts/charts";
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
  LineChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
]);

export default {
  name: "InvestmentAccountGrowth",
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
          // data: ['A', 'B', 'C', 'D', 'E'],
          data: this.last12Months(),
          inverse: true //flips the axis
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '$ {value}'
          }
        },
        series: [
          {
            type: 'line',
            // data: [10, 22, 28, 23, 19],
            smooth: true,
            data: this.accountHistoriesData(),
          }
        ],
        title: {
          text: 'Investment Account Growth',
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
      this.$store.dispatch("fetchAccountHistories");
    },
    accountHistoriesData() {
      const accountHistories = this.$store.getters.getAccountHistories;
      console.log('test data!!!');
      console.log(accountHistories);

      const monthsArray = this.last12Months();
      console.log(monthsArray);
      let accountHistoryDataArray = [];

      for (let i = 0; i < monthsArray.length; i++) {
        const monthAccountHistoryData = accountHistories.filter(e => e.dateObserved.includes(monthsArray[i]));
        if (monthAccountHistoryData.length == 1) {
          accountHistoryDataArray.push(monthAccountHistoryData[0].balanceObserved);
        }
        else {
          accountHistoryDataArray.push(accountHistoryDataArray[accountHistoryDataArray.length - 1]); //repeat the prior index
        }
      }
      console.log(accountHistoryDataArray);

      return accountHistoryDataArray;
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
      'getAccountHistories',
    ]),
  },
};
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>