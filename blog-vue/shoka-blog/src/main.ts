
import { createApp } from "vue";
import App from "./App.vue";
import { setupDirectives } from "./directives";
import { setupAssets, setupLazy, setupMasonry, setupMdPreview, setupViewer } from "./plugins";
import { setupRouter } from "./router";
import { setupStore } from "./store";

async function setupApp() {
	setupAssets();

	const app = createApp(App);

	setupStore(app);

  setupDirectives(app);

	setupLazy(app);

	setupMdPreview(app);

	setupMasonry(app);

	setupViewer(app);

	await setupRouter(app);

	app.mount("#app");
}

setupApp();
