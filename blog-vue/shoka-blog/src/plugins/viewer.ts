import VueViewer from "v-viewer";
import "viewerjs/dist/viewer.css";
import { App } from "vue";

export default function setupViewer(app: App) {
	app.use(VueViewer);
}
