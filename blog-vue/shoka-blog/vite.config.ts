import { defineConfig, loadEnv } from "vite";
import { getServiceEnvConfig } from "./.env-config";
import {
	createViteProxy,
	getRootPath,
	getSrcPath,
	setupVitePlugins,
} from "./build";
export default defineConfig((configEnv) => {
	const viteEnv = loadEnv(
		configEnv.mode,
		process.cwd()
	) as ImportMetaEnv;

	const rootPath = getRootPath();
	const srcPath = getSrcPath();

	const isOpenProxy = viteEnv.VITE_HTTP_PROXY === "Y";
	const envConfig = getServiceEnvConfig(viteEnv);

	return {
		base: viteEnv.VITE_BASE_URL,
		resolve: {
			alias: {
				"~": rootPath,
				"@": srcPath,
			},
		},
		define: {
			__VUE_PROD_HYDRATION_MISMATCH_DETAILS__: false,
		},
		plugins: setupVitePlugins(viteEnv),
		server: {
			host: "0.0.0.0",
			port: 1314,
			open: true,
			proxy: createViteProxy(isOpenProxy, envConfig),
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
