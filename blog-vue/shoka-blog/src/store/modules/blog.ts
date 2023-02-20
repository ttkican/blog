import { BlogInfo, SiteConfig } from "@/api/blogInfo/types";

const useBlogStore = defineStore("useBlogStore", {
  state: (): BlogInfo => ({
    articleCount: 0,
    categoryCount: 0,
    tagCount: 0,
    viewCount: 0,
    siteConfig: {} as SiteConfig,
  }),
  actions: {
    setBlogInfo(blogInfo: BlogInfo) {
      this.articleCount = blogInfo.articleCount;
      this.categoryCount = blogInfo.categoryCount;
      this.tagCount = blogInfo.tagCount;
      this.viewCount = blogInfo.viewCount;
      this.siteConfig = blogInfo.siteConfig;
    },
  },
  getters: {},
  persist: {
    key:"blog",
    storage: sessionStorage,
  },
});

export default useBlogStore;
