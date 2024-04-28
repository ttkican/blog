import unocss from "@unocss/vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import type { PluginOption } from "vite";
import progress from "vite-plugin-progress";
import VueDevtools from "vite-plugin-vue-devtools";
import compress from "./compress";
import unplugin from "./unplugin";
import visualizer from "./visualizer";

/**
 * vite插件
 * @param viteEnv - 环境变量配置
 */
export function setupVitePlugins(
	viteEnv: Env.ImportMeta
): (PluginOption | PluginOption[])[] {
	const plugins = [
		vue(),
		vueJsx(),
		VueDevtools(),
		...unplugin(viteEnv),
		unocss(),
		progress(),
	];

	if (viteEnv.VITE_VISUALIZER === "Y") {
		plugins.push(visualizer as PluginOption);
	}

	if (viteEnv.VITE_COMPRESS === "Y") {
		plugins.push(compress(viteEnv));
	}

	return plugins;
}
