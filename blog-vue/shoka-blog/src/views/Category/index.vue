<template>
  <div class="page-header">
    <h1 class="page-title">分类</h1>
    <img class="page-cover" src="https://ik.imagekit.io/nicexl/Wallpaper/ba41a32b219e4b40ad055bbb52935896_Y0819msuI.jpg"
      alt="">
    <!-- 波浪 -->
    <Waves></Waves>
  </div>
  <div class="bg">
    <div class="page-container">
      <Echarts :options="categoryOption"></Echarts>
      <ul class="category-list">
        <li class="category-item" v-for="category in categoryList" :key="category.id">
          <router-link :to="`/category/${category.id}`">{{ category.categoryName }}</router-link>
          <span class="category-count">({{ category.articleCount }})</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getCategoryList } from "@/api/category";
import { Category } from "@/api/category/types";
import Echarts from "@/components/Echarts/index.vue";
let categoryOption = reactive({
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  title: {
    text: "文章分类统计图🎉",
    x: "center",
  },
  legend: {
    top: "bottom",
  },
  series: [
    {
      name: '分类统计',
      type: 'pie',
      radius: [35, 130],
      center: ['50%', '47%'],
      roseType: 'area',
      itemStyle: {
        borderRadius: 6
      },
      data: [
      ] as {
        value: number;
        name: string;
      }[],
    }
  ]
});
const categoryList = ref<Category[]>([]);
onMounted(() => {
  getCategoryList().then(({ data }) => {
    categoryList.value = data.data;
    if (data.data != null) {
      data.data.forEach((item) => {
        categoryOption.series[0].data.push({
          value: item.articleCount,
          name: item.categoryName,
        });
      });
    }
  })
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.category-list {
  @include flex;
  flex-wrap: wrap;
  margin: 3rem 0 1rem 0;
}

.category-item {
  padding: 0.12em 1.2em 0.12em 1.4em;
}

.category-item:before {
  display: inline-block;
  position: relative;
  left: -0.75rem;
  width: 12px;
  height: 12px;
  border: 0.2rem solid var(--color-blue);
  border-radius: 50%;
  background: var(--grey-0);
  content: "";
  transition-duration: 0.3s;
}

.category-item:hover:before {
  border: 0.2rem solid var(--color-orange);
}

.category-item a:hover {
  transition: all 0.3s;
  color: #8e8cd8;
}

.category-item a:not(:hover) {
  transition: all 0.3s;
}

.category-count {
  margin-left: 0.5rem;
  font-size: 0.95rem;
  color: var(--grey-5);
}
</style>