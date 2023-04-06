<template>
  <router-link to="/talk" class="talk-swiper" v-if="talkList.length > 0">
    <svg-icon icon-class="laba" size="1.25rem"></svg-icon>
    <swiper class="swiper-container" :direction="'vertical'" :speed="2000" :modules="modules" :loop="true"
      :slides-per-view="1" :autoplay="{ delay: 3000, disableOnInteraction: false, }">
      <swiper-slide v-for="(talk, index) in talkList" :key="index">
        <div class="slide-content" v-html="talk"></div>
      </swiper-slide>
    </swiper>
    <svg-icon icon-class="right-arrow" class="arrow"></svg-icon>
  </router-link>
</template>

<script setup lang="ts">
import { getTalkHomeList } from '@/api/talk';
import { Autoplay } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/vue';
// 自动播放
const modules = [Autoplay];
const talkList = ref<string[]>([]);
onMounted(() => {
  getTalkHomeList().then(({ data }) => {
    talkList.value = data.data;
  })
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.talk-swiper {
  @include flex;
  margin: 0 0.5rem;
  padding: 0.6rem 1rem;
  font-size: 0.9375rem;
  border-radius: 0.5rem;
  box-shadow: 0 0.625rem 1.875rem -0.9375rem var(--box-bg-shadow);
  transition: all 0.2s ease-in-out 0s;

  &:hover {
    box-shadow: 0 0 2rem var(--box-bg-shadow);
  }
}

.swiper-container {
  width: 100%;
  height: 1.5625rem;
  line-height: 1.5625rem;
  border-radius: 0.75rem;
}

.slide-content {
  width: 100%;
  height: 100%;
  text-align: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.arrow {
  animation: 1.5s passing infinite;
}

@keyframes passing {
  0% {
    transform: translateX(-50%);
    opacity: 0;
  }

  50% {
    transform: translateX(0);
    opacity: 1;
  }

  100% {
    transform: translateX(50%);
    opacity: 0;
  }
}
</style>