interface Window {
  $dialog?: import("naive-ui").DialogProviderInst;
  $message?: import("naive-ui").MessageProviderInst;
  $notification?: import("naive-ui").NotificationProviderInst;
}

// 解决vue-cropper打包错误
declare module "*.vue" {
	import type { DefineComponent } from "vue";
	const component: DefineComponent<{}, {}, any>;
	export default component;
}

interface ImportMeta {
	readonly env: Env.ImportMeta;
}


declare module "APlayer";
declare module "@kangc/v-md-editor";
declare module "@kangc/v-md-editor/lib/preview";
declare module "@kangc/v-md-editor/lib/plugins/katex/cdn";
declare module "@kangc/v-md-editor/lib/theme/vuepress.js";
declare module "@kangc/v-md-editor/lib/theme/vuepress-parser.js";
declare module "@kangc/v-md-editor/lib/plugins/todo-list/index";
declare module "vue-cropper";
