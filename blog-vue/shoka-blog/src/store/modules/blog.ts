import { BlogInfo, SiteConfig } from "@/api/blogInfo/types";
import { BlogState } from "../types";

const useBlogStore = defineStore("useBlogStore", {
  state: (): BlogState => ({
    blogInfo: {
      siteConfig: {} as SiteConfig,
    } as BlogInfo,
  }),
  actions: {
    setBlogInfo(blogInfo: BlogInfo) {
      this.blogInfo = blogInfo;
    },
  },
  getters: {},
  persist: {
    key: "blog",
    storage: sessionStorage,
  },
});

export default useBlogStore;
