import { App } from "vue";
import { VueMasonryPlugin } from "vue-masonry";

export default function setupMasonry(app: App) {
	app.use(VueMasonryPlugin);
}
