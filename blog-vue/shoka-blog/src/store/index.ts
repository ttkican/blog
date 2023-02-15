import useAppStore from "./modules/app";
import useBlogStore from "./modules/blog";
import useUserStore from "./modules/user";

const useStore = () => ({
  app: useAppStore(),
  blog: useBlogStore(),
  user: useUserStore(),
});

export default useStore;
