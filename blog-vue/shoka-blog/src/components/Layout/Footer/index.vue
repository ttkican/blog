<template>
  <footer class="footer-wrapper" v-if="!isMessage">
    <p>
      © {{ formatDate(blog.blogInfo.siteConfig.createSiteTime, "YYYY") }} -
      {{ new Date().getFullYear() }} By
      {{ blog.blogInfo.siteConfig.siteAuthor }}
    </p>
    <a href="https://beian.miit.gov.cn/" target="_blank">
      {{ blog.blogInfo.siteConfig.recordNumber }}
    </a>
  </footer>
</template>

<script setup lang="ts">
import { useBlogStore } from "@/store";
import { formatDate } from "@/utils/date";
const route = useRoute();
const blog = useBlogStore();
const isMessage = computed(() => route.path == "/message");
</script>

<style lang="scss" scoped>
.footer-wrapper {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  width: 100%;
  height: 8rem;
  padding: 2.5rem 0;
  font-size: 0.875rem;
  text-align: center;
  color: var(--grey-0);
  background: var(--footer-bg);
  background-size: 300% 300%;
  -webkit-animation: gradientBG 10s ease infinite;
  animation: gradientBG 10s ease infinite;
}

@keyframes gradientBG {
  0% {
    background-position: 0 50%;
  }

  50% {
    background-position: 100% 50%;
  }

  100% {
    background-position: 0 50%;
  }
}
</style>
