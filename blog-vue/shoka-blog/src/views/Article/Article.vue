<template>
  <div class="page-header" v-if="article">
    <div class="page-title">
      <h1 class="article-title">{{ article.articleTitle }}</h1>
      <div class="article-meta">
        <div class="first-meta">
          <span><svg-icon icon-class="calendar" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">发表于 </span>{{ formatDate(article.createTime) }}
          </span>
          <span class="item" v-if="article.updateTime"><svg-icon icon-class="update"
              style="margin-right:0.15rem;"></svg-icon>
            <span class="text">更新于 </span>{{ formatDate(article.updateTime) }}
          </span>
          <span class="item"><svg-icon icon-class="eye" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">阅读量 </span>{{ article.viewCount }}</span>
        </div>
        <div class="second-meta">
          <span><svg-icon icon-class="edit" size="0.9rem" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">字数统计 </span>{{ count(wordNum) }} 字
          </span>
          <span class="item"><svg-icon icon-class="clock" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">阅读时长 </span>{{ readTime }} 分钟
          </span>
          <span class="item">
            <svg-icon icon-class="category" style="margin-right:0.15rem;"></svg-icon>{{
              article.category.categoryName
            }}
          </span>
        </div>
      </div>
    </div>
    <img class="page-cover" :src="article.articleCover" alt="">
    <!-- 波浪 -->
    <Waves></Waves>
  </div>
  <div class="bg">
    <div class="main-container" v-if="article">
      <div class="left-container" :class="app.sideFlag ? 'w-full' : ''">
        <div class="article-container">
          <v-md-preview ref="articleRef" class="md" v-viewer :text="article.articleContent"></v-md-preview>
          <div class="article-post">
            <div class="tag-share">
              <router-link :to="`/tag/${tag.id}`" class="article-tag" v-for="tag in article.tagVOList" :key="tag.id">
                <svg-icon icon-class="tag" size="0.8rem"></svg-icon>
                {{ tag.tagName }}
              </router-link>
              <Share class="share-info" :url="articleHref" :title="article.articleTitle"></Share>
            </div>
            <div class="reward">
              <button class="btn" :class="isLike(article.id)" @click="like">
                <svg-icon icon-class="like" size="0.9rem"></svg-icon> 点赞
                <span>{{ article.likeCount }}</span>
              </button>
              <n-popover trigger="click" v-if="blog.blogInfo.siteConfig.isReward">
                <template #trigger>
                  <button class="btn reward-btn">
                    <svg-icon icon-class="qr_code" size="0.9rem"></svg-icon>
                    打赏
                  </button>
                </template>
                <div class="reward-all">
                  <span>
                    <img class="reward-img" v-lazy="blog.blogInfo.siteConfig.weiXinCode" />
                    <div class="reward-desc">微信</div>
                  </span>
                  <span style="margin-left: 0.3rem;">
                    <img class="reward-img" v-lazy="blog.blogInfo.siteConfig.aliCode" />
                    <div class="reward-desc">支付宝</div>
                  </span>
                </div>
              </n-popover>
              <p class="tea" v-if="blog.blogInfo.siteConfig.isReward">请我喝[茶]~(￣▽￣)~*</p>
            </div>
            <div class="copyright">
              <ul>
                <li class="author">
                  <svg-icon icon-class="author" size="0.9rem" style="margin-right:0.3rem"></svg-icon>
                  <strong>本文作者： </strong>{{ blog.blogInfo.siteConfig.siteAuthor }}
                </li>
                <li class="link">
                  <svg-icon icon-class="article_link" size="0.9rem" style="margin-right:0.3rem"></svg-icon>
                  <strong>本文链接：</strong>
                  <a :href="articleHref">{{ articleHref }}</a>
                </li>
                <li class="license">
                  <svg-icon icon-class="article_share" size="0.8rem" style="margin-right:0.3rem"></svg-icon>
                  <strong>版权声明： </strong>本站所有文章除特别声明外，均采用
                  <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh" target="_blank">CC
                    BY-NC-SA 4.0</a>
                  许可协议。转载请注明文章出处！
                </li>
              </ul>
            </div>
            <!-- 上下文 -->
            <div class="post-nav">
              <div class="item" v-if="article.lastArticle">
                <router-link :to="`/article/${article.lastArticle?.id}`" class="post-cover"
                  :style="articleCover(article.lastArticle.articleCover)">
                  <span class="post-last-next">上一篇</span>
                  <h3 class="post-title">{{ article.lastArticle.articleTitle }}</h3>
                </router-link>
              </div>
              <div class="item" v-if="article.nextArticle">
                <router-link :to="`/article/${article.nextArticle?.id}`" class="post-cover"
                  :style="articleCover(article.nextArticle.articleCover)">
                  <span class="post-last-next">下一篇</span>
                  <h3 class="post-title">{{ article.nextArticle.articleTitle }}</h3>
                </router-link>
              </div>
            </div>
            <CommentList :comment-type="commentType"></CommentList>
          </div>
        </div>
      </div>
      <div class="right-container" :class="app.sideFlag ? 'hidden' : ''">
        <div class="side-card">
          <Catalog v-if="articleLoaded" :domRef="articleRef"></Catalog>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getArticle, likeArticle } from "@/api/article";
import { ArticleInfo, ArticlePagination } from "@/api/article/types";
import { CategoryVO } from "@/api/category/types";
import { useAppStore, useBlogStore, useUserStore } from "@/store";
import { formatDate } from "@/utils/date";
import { Share } from 'vue3-social-share';
import 'vue3-social-share/lib/index.css';
const user = useUserStore();
const app = useAppStore();
const blog = useBlogStore();
const articleRef = ref();
const route = useRoute();
const articleHref = window.location.href;
const data = reactive({
  articleLoaded: false,
  wordNum: 0,
  readTime: 0,
  commentType: 1,
  article: {
    id: 0,
    articleCover: "",
    articleTitle: "",
    articleContent: "",
    articleType: 0,
    viewCount: 0,
    likeCount: 0,
    category: {} as CategoryVO,
    tagVOList: [],
    createTime: "",
    lastArticle: {} as ArticlePagination,
    nextArticle: {} as ArticlePagination,
    updateTime: ""
  } as ArticleInfo,
});
const { articleLoaded, wordNum, readTime, commentType, article } = toRefs(data);
const articleCover = computed(() => (cover: string) => 'background-image:url(' + cover + ')');
const isLike = computed(() => (id: number) => user.articleLikeSet.indexOf(id) != -1 ? "like-btn-active" : "like-btn");
const count = (value: number) => {
  if (value >= 1000) {
    return (value / 1000).toFixed(1) + "k";
  }
  return value;
};
const deleteHTMLTag = (content: string) => {
  return content
    .replace(/<\/?[^>]*>/g, "")
    .replace(/[|]*\n/, "")
    .replace(/&npsp;/gi, "");
};
const like = () => {
  if (!user.id) {
    app.setLoginFlag(true);
    return;
  }
  let id = article.value.id;
  likeArticle(id).then(({ data }) => {
    if (data.flag) {
      //判断是否点赞
      if (user.articleLikeSet.indexOf(id) != -1) {
        article.value.likeCount -= 1;
      } else {
        article.value.likeCount += 1;
      }
      user.articleLike(id);
    }
  });
};
onMounted(() => {
  getArticle(Number(route.params.id)).then(({ data }) => {
    article.value = data.data;
    document.title = article.value.articleTitle;
    wordNum.value = deleteHTMLTag(article.value.articleContent).length;
    readTime.value = Math.round(wordNum.value / 400);
    articleLoaded.value = true;
  })
})
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.article-container {
  border-radius: 0.5rem;
  overflow: hidden;
  box-shadow: 0 0 1rem var(--box-bg-shadow);
}

.article-post {
  margin: 0 2rem;
  padding-bottom: 1rem;
}

.article-title {
  font-weight: 500;
  font-size: 2.5rem;
  letter-spacing: 0.125rem;
  text-align: center;
  color: var(--header-text-color);
}

.article-meta {
  @include flex;
  flex-direction: column;
  font-size: 0.875rem;

  .item {
    margin-left: 0.625rem;
  }
}

.tag-share {
  display: flex;
  align-items: center;

  .share-info {
    margin-left: auto;
  }
}

.reward {
  margin: 1.25rem auto;
  padding: 0.625rem 0;
  text-align: center;

  .btn {
    border-radius: 0.3125rem;
    color: var(--grey-0);
    cursor: pointer !important;
    padding: 0 0.9375rem;
    font: inherit;
  }

  .like-btn-active {
    background: var(--primary-color);
  }

  .like-btn {
    background: #999;
  }

  .reward-btn {
    position: relative;
    margin-left: 1rem;
    background: var(--primary-color);
  }

  .tea {
    font-size: 0.8125em;
    color: var(--grey-5);
    margin-top: 0.5rem;
  }
}

.reward-all {
  display: flex;
  align-items: center;
}

.reward-img {
  width: 130px;
  height: 130px;
  display: block;
}

.reward-desc {
  margin: -5px 0;
  color: #858585;
  text-align: center;
}

.copyright {
  font-size: 0.75em;
  padding: 1rem 2rem;
  margin-bottom: 2.5rem;
  border-radius: 0.625rem;
  background: var(--grey-2);
  color: var(--grey-6);
}

.post-nav {
  display: flex;
  margin-bottom: 2.5rem;
  border-radius: 0.625rem;
  overflow: hidden;

  .item {
    width: 50%;
  }

  .post-cover {
    display: flex;
    flex-direction: column;
    color: var(--header-text-color);
    padding: 1.25rem 2.5rem;
    background-size: cover;
    animation: blur 0.8s ease-in-out forwards;

    &:before {
      content: "";
      position: absolute;
      width: 100%;
      height: 100%;
      background: linear-gradient(135deg, #434343, #000);
      opacity: 0.5;
      transition: all 0.2s ease-in-out 0s;
      z-index: -1;
      top: 0;
      left: 0;
    }
  }

  .post-last-next {
    font-size: 0.8125rem;
  }
}

.post-cover:hover::before {
  opacity: 0.4;
}

@media (max-width: 767px) {
  .article-title {
    font-size: 1.5rem;
  }

  .article-meta .text {
    display: none;
  }

  .article-post {
    margin: 0 0.5rem;
  }

  .post-nav {
    flex-direction: column;
  }

  .post-nav .item {
    width: 100%;
  }

  .reward-img {
    width: 105px;
    height: 105px;
  }

}
</style>
