import type { App } from "vue";
import setupAnimate from "./animate";

/** setup custom vue directives. - [安装自定义的vue指令] */
export function setupDirectives(app: App) {
	setupAnimate(app);
}
