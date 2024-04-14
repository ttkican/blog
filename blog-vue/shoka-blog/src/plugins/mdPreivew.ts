import createKatexPlugin from "@kangc/v-md-editor/lib/plugins/katex/cdn";
import createTodoListPlugin from "@kangc/v-md-editor/lib/plugins/todo-list/index";
import "@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css";
import VMdPreview from "@kangc/v-md-editor/lib/preview";
import "@kangc/v-md-editor/lib/theme/style/vuepress.css";
import vuepressTheme from "@kangc/v-md-editor/lib/theme/vuepress.js";
import Prism from "prismjs";
import { App } from "vue";

export default function setupMdPreview(app: App) {
	VMdPreview.use(vuepressTheme, {
		Prism,
	})
		.use(createTodoListPlugin())
		.use(createKatexPlugin());
	app.use(VMdPreview);
}
