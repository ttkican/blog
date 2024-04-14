<template>
	<!-- <meting-js v-if="blog.blogInfo.siteConfig.isMusic" server="netease" type="playlist"
		:id="blog.blogInfo.siteConfig.musicId" fixed="true" theme="#e9546b"></meting-js> -->
	<aplayer server="netease" type="playlist" :id="blog.blogInfo.siteConfig.musicId" :fixed=true theme="#e9546b">
	</aplayer>
</template>

<script setup lang="ts">
import { useBlogStore } from "@/store";
import aplayer from "./aplayer.vue";
const blog = useBlogStore();
</script>

<style scoped></style>
