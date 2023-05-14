<template>
  <div class="page-header">
    <h1 class="page-title">归档</h1>
    <img class="page-cover" src="https://ik.imagekit.io/nicexl/Wallpaper/ba41a32b219e4b40ad055bbb52935896_Y0819msuI.jpg"
      alt="">
    <!-- 波浪 -->
    <Waves></Waves>
  </div>
  <div class="bg">
    <div class="page-container">
      <div class="archive-title">文章总览 - {{ count }}</div>
      <div class="archive-list">
        <div class="archive-item" v-for="archive in archivesList" :key="archive.id">
          <router-link class="article-cover" :to="`/article/${archive.id}`">
            <img class="cover" v-lazy="archive.articleCover">
          </router-link>
          <div class="article-info">
            <div class="article-time">
              <svg-icon icon-class="calendar" style="margin-right:0.4rem;"></svg-icon>
              <time>{{ formatDate(archive.createTime) }}</time>
            </div>
            <router-link class="article-title" :to="`/article/${archive.id}`">
              {{ archive.articleTitle }}
            </router-link>
          </div>
        </div>
      </div>
      <Pagination v-if="count > 0" v-model:current="queryParams.current" :total="Math.ceil(count / 5)">
      </Pagination>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getArchivesList } from '@/api/archives';
import { Archives } from '@/api/archives/types';
import Pagination from '@/components/Pagination/index.vue';
import { PageQuery } from '@/model';
import { formatDate } from '@/utils/date';

const data = reactive({
  count: 0,
  queryParams: {
    current: 1,
    size: 5,
  } as PageQuery,
  archivesList: [] as Archives[],
});
const {
  count,
  queryParams,
  archivesList,
} = toRefs(data);
watch(
  () => queryParams.value.current,
  () => {
    getArchivesList(queryParams.value).then(({ data }) => {
      archivesList.value = data.data.recordList;
      count.value = data.data.count;
    });
  }
);
onMounted(() => {
  getArchivesList(queryParams.value).then(({ data }) => {
    archivesList.value = data.data.recordList;
    count.value = data.data.count;
  });
})
</script>

<style lang="scss" scoped>
.archive-title {
  position: relative;
  margin-left: 10px;
  padding-bottom: 20px;
  padding-left: 20px;
  font-size: 1.5rem;

  &::before {
    position: absolute;
    top: 16px;
    left: -8px;
    z-index: 1;
    width: 18px;
    height: 18px;
    border: 5px solid var(--color-blue);
    border-radius: 10px;
    content: '';
    line-height: 10px;
  }

  &::after {
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 0;
    width: 2px;
    height: 1.5em;
    background: #aadafa;
    content: '';
  }
}

.archive-title:hover:before {
  border-color: var(--color-orange);
}

.archive-list {
  margin-left: 10px;
  padding-left: 20px;
  border-left: 2px solid #aadafa;
}

.archive-item {
  position: relative;
  display: flex;
  align-items: center;
  margin: 0 0 20px 10px;

}

.archive-item:hover:before {
  border-color: var(--color-orange);
}

.archive-item::before {
  position: absolute;
  left: -36px;
  width: 10px;
  height: 10px;
  border: 3px solid var(--color-blue);
  border-radius: 6px;
  background: var(--grey-0);
  content: '';
}

.article-cover {
  width: 120px;
  height: 120px;
  overflow: hidden;
  border-radius: 12px;

  .cover {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: filter 375ms ease-in 0.2s, transform 0.6s;
  }
}

.cover:hover {
  transform: scale(1.1);
}

.article-info {
  display: flex;
  flex-direction: column;
  flex: 1;
  margin: 0 1rem;

  .article-time {
    font-size: 14px;
  }

  .article-title {
    font-size: 0.9rem;
    margin: 2px 0;
  }
}

.article-title:hover {
  color: var(--primary-color);
  transform: translateX(10px);

}
</style>