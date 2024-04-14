import { BlogInfo, SiteConfig } from "@/api/blogInfo/types";
/**
 * 博客
 */
interface BlogState {
  /**
   * 博客信息
   */
  blogInfo: BlogInfo;
}

export const useBlogStore = defineStore("useBlogStore", {
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
