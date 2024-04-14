import { router } from "@/router";
import { useUserStore } from "@/store";
import { getToken } from "@/utils/token";
import NProgress from "nprogress";

NProgress.configure({
  easing: "ease",
  speed: 500,
  showSpinner: false,
  trickleSpeed: 200,
  minimum: 0.3,
});

router.beforeEach((to, from, next) => {
  NProgress.start();
  const user = useUserStore();
  if (to.meta.title) {
    document.title = to.meta.title as string;
  }
  if (getToken()) {
    if (user.id === undefined) {
      user
        .GetUserInfo()
        .then(() => next())
        .catch(() => {
          user.LogOut().then(() => {
            window.$message?.warning("凭证失效，请重新登录");
            next();
          });
        });
    } else {
      next();
    }
  } else {
    next();
  }
});
router.afterEach(() => {
  NProgress.done();
});
