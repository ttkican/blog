import dayjs from "dayjs";
import { fileURLToPath } from "node:url";
import { defineConfig, loadEnv } from "vite";
import {
	createViteProxy,
	setupVitePlugins
} from "./build";
export default defineConfig((configEnv) => {
	const viteEnv = loadEnv(
		configEnv.mode,
		process.cwd()
	) as Env.ImportMeta;

	const buildTime = dayjs().format("YYYY-MM-DD HH:mm:ss");

	return {
		base: viteEnv.VITE_BASE_URL,
		resolve: {
			alias: {
				"~": fileURLToPath(new URL("./", import.meta.url)),
				"@": fileURLToPath(new URL("./src", import.meta.url)),
			},
		},
		define: {
			__VUE_PROD_HYDRATION_MISMATCH_DETAILS__: false,
			BUILD_TIME: JSON.stringify(buildTime),
		},
		plugins: setupVitePlugins(viteEnv),
		server: {
			host: "0.0.0.0",
			port: 1314,
			open: true,
			proxy: createViteProxy(viteEnv),
		},
		build: {
			reportCompressedSize: false,
			sourcemap: false,
			outDir: viteEnv.VITE_DIST_NAME,
			commonjsOptions: {
				ignoreTryCatch: false,
			},
		},
	};
});
