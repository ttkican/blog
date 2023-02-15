import "@/assets/styles/index.scss";
import CalendarHeatmap from "@/components/CalendarHeatmap/index.vue";
import Echarts from "@/components/Echarts/index.vue";
import Pagination from "@/components/Pagination/index.vue";
import RightToolbar from "@/components/RightToolbar/index.vue";
import SvgIcon from "@/components/SvgIcon/index.vue";
import * as directive from "@/directive";
import "@/permission";
import router from "@/router";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import MdEditor from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import "nprogress/nprogress.css";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import "virtual:svg-icons-register";
import { createApp, Directive } from "vue";
import App from "./App.vue";

const app = createApp(App);
const pinia = createPinia();
const DropdownToolbar = MdEditor.DropdownToolbar;

Object.keys(directive).forEach((key) => {
  app.directive(key, (directive as { [key: string]: Directive })[key]);
});

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
pinia.use(piniaPluginPersistedstate);
app.use(pinia);
app.use(router);
app.component("CalendarHeatmap", CalendarHeatmap);
app.component("md-editor", MdEditor);
app.component("dropdown-toolbar", DropdownToolbar);
app.component("svg-icon", SvgIcon);
app.component("Pagination", Pagination);
app.component("RightToolbar", RightToolbar);
app.component("Echarts", Echarts);
app.use(ElementPlus);
app.mount("#app");
