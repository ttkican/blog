<template>
  <component :is="type" v-bind="linkProps()">
    <slot />
  </component>
</template>

<script setup lang="ts">
import { computed } from 'vue';
const props = defineProps({
  to: {
    type: [String, Object],
    required: true
  }
});
const type = computed(() => {
  return 'router-link'
});

const linkProps = () => {
  return {
    to: props.to
  }
}
</script>
