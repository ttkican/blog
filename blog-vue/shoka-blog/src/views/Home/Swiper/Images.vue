<template>
  <div class="imgs">
    <ul>
      <li class="item" v-for="(image, index) in imageList" :key="index" :style="{
        'background-image': 'url(' + image + ')'
      }">
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">

const imageList = [
  "https://static.ttkwsd.top/articles/379ce4b2ddf4b087441c196fbffa2f3b.jpg",
  "https://static.ttkwsd.top/articles/80ae8255603788a1305d4bcc177097a2.jpg",
  "https://static.ttkwsd.top/articles/6becdfb5062ec2e1f9b9b16dd4b65d7e.jpg",
  "https://static.ttkwsd.top/articles/2ce1129fceee7f14cb4cf554ed167534.jpg",
  "https://static.ttkwsd.top/articles/84aae58f4246f8419cf018d7d7f6bae8.jpg",
  "https://static.ttkwsd.top/articles/5b43ce3015351615d3654b8cac53c525.jpg"
]
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.imgs {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  z-index: -9;
  background-color: #363636;
  overflow: hidden;

  .item {
    @include absolute;
    width: 100%;
    height: 100%;
    background: no-repeat 50% 50% / cover;
    opacity: 0;
    animation: imageAnimation 36s linear infinite 0s;
    backface-visibility: hidden;
    transform-style: preserve-3d;

    &:nth-child(2) {
      animation-delay: 6s;
    }

    &:nth-child(3) {
      animation-delay: 12s;
    }

    &:nth-child(4) {
      animation-delay: 18s;
    }

    &:nth-child(5) {
      animation-delay: 24s;
    }

    &:nth-child(6) {
      animation-delay: 30s;
    }
  }

  &::before {
    content: '';
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, .2);
    transition: all .2s ease-in-out 0s;
  }
}

@keyframes imageAnimation {
  0% {
    opacity: 0;
    animation-timing-function: ease-in;
  }

  2% {
    opacity: 1;
  }

  8% {
    opacity: 1;
    transform: scale(1.05);
    animation-timing-function: ease-out;
  }

  17% {
    opacity: 1;
    transform: scale(1.1);
  }

  25% {
    opacity: 0;
    transform: scale(1.1);
  }

  100% {
    opacity: 0;
  }
}
</style>