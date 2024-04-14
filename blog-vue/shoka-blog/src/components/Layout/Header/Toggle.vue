<template>
  <div :class="drawerVisible(app.isCollapse)" @click="app.isCollapse = true">
    <div class="lines">
      <span class="line"></span>
      <span class="line"></span>
      <span class="line"></span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAppStore } from "@/store";
const app = useAppStore();

const drawerVisible = computed(() => (value: boolean) => value ? "toggle close" : "toggle");
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.toggle {
  display: none;

  .lines {
    width: 1.375rem;
    padding: 1.25rem;
    box-sizing: unset;
  }
}

.toggle.close {
  .line:nth-child(1) {
    transform: rotate(-45deg);
    top: 0.3125rem;
  }

  .line:nth-child(2) {
    opacity: 0;
  }

  .line:nth-child(3) {
    transform: rotate(45deg);
    top: -0.3125rem;
  }
}

.toggle {
  line-height: 0;
  cursor: pointer;

  .line {
    display: inline-block;
    position: relative;
    top: 0;
    left: 0;
    width: 100%;
    height: 0.125rem;
    border-radius: 0.0625rem;
    background: var(--header-text-color);
    vertical-align: top;
    box-shadow: 0 0 0.5rem rgb(0 0 0 / 50%);
    transition: all 0.4s;

    &:not(:first-child) {
      margin-top: 0.1875rem;
    }
  }
}

.show .line {
  background: var(--text-color);
  box-shadow: 0 0 0.0625rem var(--grey-9-a1);
}

@media (max-width: 991px) {
  .toggle {
    @include flex;
    flex-direction: column;
  }
}
</style>
