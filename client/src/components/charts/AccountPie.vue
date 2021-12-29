<template>
  <v-chart class="chart" :option="option" />
</template>

<script>
import { use } from "echarts/core";
import { CanvasRenderer } from "echarts/renderers";
import { PieChart } from "echarts/charts";
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from "echarts/components";
import VChart, { THEME_KEY } from "vue-echarts";
import {mapGetters} from "vuex";

use([
  CanvasRenderer,
  PieChart,
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
  created() {
    this.$store.dispatch("fetchAccounts");
  },
  computed: {
    ...mapGetters([
      'getAccounts',
    ]),
  },
  methods: {
    getAccountBalances() {
      let accountBalances = [];
      const accounts = this.$store.getters.getAccounts;
      accounts.forEach(e => accountBalances.push({value: e.balance, name: e.name }));
      return accountBalances;
    },
    getAccountNames() {
      let accountNames = [];
      const accounts = this.$store.getters.getAccounts;
      accounts.forEach(e => accountNames.push(e.name));
      return accountNames;
    },
  },
  data() {
    return {
      option: {
        title: {
          text: "Account Distribution",
          left: "center"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient: "vertical",
          left: "left",
          data: this.getAccountNames()
        },
        series: [
          {
            name: "Traffic Sources",
            type: "pie",
            radius: "55%",
            center: ["50%", "60%"],
            data: this.getAccountBalances(),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      }
    };
  }
};
</script>

<style scoped>
.chart {
  height: 400px;
}
</style>