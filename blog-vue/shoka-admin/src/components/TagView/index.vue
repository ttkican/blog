<template>
  <div class="tags-view-container">
    <scroll-pane ref="scrollPaneRef" class="tags-view-wrapper" @scroll="handleScroll">
      <router-link v-for="item in visitedViews" :key="item.path" :data-path="item.path"
        :class="isActive(item) ? 'active' : ''" :to="{ path: item.path, query: item.query }" class="tags-view-item"
        @click.middle="!isAffix(item) ? closeSelectedTag(item) : ''" @contextmenu.prevent="openMenu(item, $event)">
        {{ item.meta?.title }}
        <span v-if="!isAffix(item)" class="icon-close" @click.prevent.stop="closeSelectedTag(item)">
          <svg-icon icon-class="close" size="0.9rem" />
        </span>
      </router-link>
    </scroll-pane>
    <ul v-show="visible" :style="{ left: left + 'px', top: top + 'px' }" class="contextmenu">
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)">
        <close style="width: 1em; height: 1em;vertical-align: -0.12em;" /> 关闭当前
      </li>
      <li @click.stop="closeOtherTags">
        <circle-close style="width: 1em; height: 1em;vertical-align: -0.12em;" /> 关闭其他
      </li>
      <li v-if="!isFirstView()" @click="closeLeftTags">
        <back style="width: 1em; height: 1em;vertical-align: -0.12em;" /> 关闭左侧
      </li>
      <li v-if="!isLastView()" @click="closeRightTags">
        <right style="width: 1em; height: 1em;vertical-align: -0.12em;" /> 关闭右侧
      </li>
      <li @click.stop="closeAllTags(selectedTag)">
        <circle-close style="width: 1em; height: 1em;vertical-align: -0.12em;" /> 关闭所有
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import useStore from "@/store";
import { TagView } from "@/store/interface";
import { ComponentInternalInstance, computed, getCurrentInstance, nextTick, onMounted, ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import ScrollPane from "./ScrollPane.vue";

const router = useRouter();
const route = useRoute();
const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { tag, permission } = useStore();
const visible = ref(false);
const top = ref(0);
const left = ref(0);
const affixTags = ref<TagView[]>([]);
const selectedTag = ref({});
const scrollPaneRef = ref();
const visitedViews = computed(() => tag.visitedViews);
const routes = computed(() => permission.routes);

const openMenu = (tag: TagView, e: MouseEvent) => {
  const menuMinWidth = 105;
  const offsetLeft = proxy?.$el.getBoundingClientRect().left; // container margin left
  const offsetWidth = proxy?.$el.offsetWidth; // container width
  const maxLeft = offsetWidth - menuMinWidth; // left boundary
  const l = e.clientX - offsetLeft + 15; // 15: margin right
  if (l > maxLeft) {
    left.value = maxLeft;
  } else {
    left.value = l;
  }
  top.value = e.clientY;
  visible.value = true;
  selectedTag.value = tag;
};
const closeSelectedTag = (view: TagView) => {
  tag.delView(view).then((res: any) => {
    if (isActive(view)) {
      toLastView(res.visitedViews, view);
    }
  });
};
const closeOtherTags = () => {
  router.push(selectedTag.value);
  tag.delOtherViews(selectedTag.value).then(() => {
    moveToCurrentTag();
  });
  closeMenu();
};
const closeAllTags = (view: TagView) => {
  tag.delAllViews().then((res: any) => {
    toLastView(res.visitedViews, view);
  });
  closeMenu();
};
const toLastView = (visitedViews: TagView[], view?: any) => {
  const latestView = visitedViews.slice(-1)[0];
  if (latestView && latestView.fullPath) {
    router.push(latestView.fullPath);
  } else {
    // now the default is to redirect to the home page if there is no tags-view,
    // you can adjust it according to your needs.
    if (view.name === 'Dashboard') {
      // to reload home page
      router.replace({ path: '/redirect' + view.fullPath });
    } else {
      router.push('/');
    }
  }
};
const isFirstView = () => {
  try {
    return (
      (selectedTag.value as TagView).fullPath ===
      tag.visitedViews[1].fullPath ||
      (selectedTag.value as TagView).fullPath === '/index'
    );
  } catch (err) {
    return false;
  }
};
const isLastView = () => {
  try {
    return (
      (selectedTag.value as TagView).fullPath ===
      tag.visitedViews[tag.visitedViews.length - 1].fullPath
    );
  } catch (err) {
    return false;
  }
};
const closeLeftTags = () => {
  tag.delLeftViews(selectedTag.value).then((res: any) => {
    if (!res.visitedViews.find((item: any) => item.fullPath === route.fullPath)) {
      toLastView(res.visitedViews);
    }
  });
}
const closeRightTags = () => {
  tag.delRightViews(selectedTag.value).then((res: any) => {
    if (!res.visitedViews.find((item: any) => item.fullPath === route.fullPath)) {
      toLastView(res.visitedViews);
    }
  });
};
const closeMenu = () => {
  visible.value = false;
};
const handleScroll = () => {
  visible.value = false;
};
const isActive = (tag: TagView) => {
  return tag.path === route.path;
};
const isAffix = (tag: TagView) => {
  return tag.meta && tag.meta.affix;
};
const addTags = () => {
  if (route.name) {
    tag.addView(route);
  }
};
const moveToCurrentTag = () => {
  nextTick(() => {
    for (const r of visitedViews.value) {
      if (r.path === route.path) {
        // when query is different then update
        if (r.fullPath !== route.fullPath) {
          tag.updateVisitedView(route);
        }
      }
    }
  });
};
const filterAffixTags = (routes: any[], basePath = '') => {
  let tags: TagView[] = [];
  routes.forEach(route => {
    if (route.meta && route.meta.affix) {
      const tagPath = getNormalPath(basePath + '/' + route.path)
      tags.push({
        fullPath: tagPath,
        path: tagPath,
        name: route.name,
        meta: { ...route.meta }
      });
    }
    if (route.children) {
      const childTags = filterAffixTags(route.children, route.path);
      if (childTags.length >= 1) {
        tags = tags.concat(childTags);
      }
    }
  });
  return tags;
}
const getNormalPath = (p: string) => {
  if (p.length === 0 || !p || p == 'undefined') {
    return p
  };
  let res = p.replace('//', '/')
  if (res[res.length - 1] === '/') {
    return res.slice(0, res.length - 1)
  }
  return res;
}

const initTags = () => {
  const res = filterAffixTags(routes.value);
  affixTags.value = res;
  for (const item of res) {
    // Must have tag name
    if ((item as TagView).name) {
      tag.addVisitedView(item);
    }
  }
};
watch(
  route,
  () => {
    addTags();
    moveToCurrentTag();
  },
  {
    //初始化立即执行
    immediate: true
  }
);
watch(visible, value => {
  if (value) {
    document.body.addEventListener('click', closeMenu);
  } else {
    document.body.removeEventListener('click', closeMenu);
  }
});
onMounted(() => {
  initTags();
});
</script>

<style lang="scss" scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  background-color: var(--el-bg-color);
  border: 1px solid var(--el-border-color-light);
  box-shadow: 0px 1px 1px var(--el-box-shadow-light);

  .tags-view-wrapper {
    .tags-view-item {
      display: inline-block;
      position: relative;
      cursor: pointer;
      height: 26px;
      line-height: 26px;
      border: 1px solid var(--el-border-color-light);
      padding: 0 8px;
      font-size: 12px;
      margin-left: 5px;
      margin-top: 4px;

      &:first-of-type {
        margin-left: 15px;
      }

      &:last-of-type {
        margin-right: 15px;
      }

      &.active {
        background-color: #42b983;
        color: #fff;
        border-color: #42b983;

        &::before {
          content: "";
          background: #fff;
          display: inline-block;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          position: relative;
          margin-right: 5px;
        }
      }

      .icon-close {
        border-radius: 50%;
        text-align: center;

        &:hover {
          background-color: #ccc;
          color: #fff;
        }
      }
    }
  }

  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);

    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;

      &:hover {
        background: #eee;
      }
    }
  }
}
</style>
