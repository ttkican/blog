<template>
    <div ref="chartDom" :style="{ 'width': width, 'height': height }"></div>
</template>

<script setup lang="ts">
import { useResizeObserver } from "@vueuse/core";
import * as echarts from "echarts";
import { markRaw, onMounted, ref, watch } from "vue";
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
        default: "300px",
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
    myChart.value = markRaw(echarts.init(chartDom.value!));
    myChart.value.setOption(props.options, true);
    useResizeObserver(chartDom.value, () => {
        myChart.value?.resize();
    })
})
</script>

<style scoped>

</style>