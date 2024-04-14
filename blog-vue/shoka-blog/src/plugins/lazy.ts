import error from "@/assets/images/404.gif";
import loading from "@/assets/images/loading.gif";
import { App } from "vue";
import lazyPlugin from "vue3-lazy";

export default function setupLazy(app: App) {
	app.use(lazyPlugin, {
		loading,
		error,
	});
}
