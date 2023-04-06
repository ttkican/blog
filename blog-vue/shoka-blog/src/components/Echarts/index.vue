<template>
  <div ref="chartDom" :style="{ 'width': width, 'height': height }"></div>
</template>

<script setup lang="ts">
import { useResizeObserver } from "@vueuse/core";
import * as echarts from "echarts";
const chartDom = ref<HTMLElement>();
const myChart = ref<echarts.EChartsType>();
const props = defineProps({
  options: {
    type: Object,
    default: {},
    required: true
  },
  width: {
    type: String,
    default: "100%",
  },
  height: {
    type: String,
    default: "400px",
  },
});
watch(
  () => props.options,
  (newOptions) => {
    myChart.value?.setOption(newOptions);
  },
  {
    deep: true,
  }
);
onMounted(() => {
  myChart.value = echarts.init(chartDom.value!);
  myChart.value.setOption(props.options, true);
  useResizeObserver(chartDom.value, () => {
    myChart.value?.resize();
  });
})
</script>

<style scoped></style>