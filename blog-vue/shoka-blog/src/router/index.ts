import { App } from "vue";
import { createRouter, createWebHistory } from "vue-router";
import { routes } from "./routes";

export const router = createRouter({
	history: createWebHistory(),
	routes,
	//滚动行为
	scrollBehavior(to, from, savedPosition) {
		// to：即将进入的路由对象
		// from：当前导航正要离开的路由对象
		// savedPosition：上次记录的滚动位置
		// 默认行为，如果有记录的滚动位置，则恢复到该位置
		if (savedPosition) {
			return savedPosition;
		}
		// 没有记录的滚动位置，则滚动到页面顶部
		return { top: 0 };
	},
});

/** setup vue router. - [安装vue路由] */
export async function setupRouter(app: App) {
	app.use(router);
	await router.isReady();
}
