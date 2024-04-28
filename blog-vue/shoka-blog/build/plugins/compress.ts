import ViteCompression from "vite-plugin-compression";

export default (viteEnv: Env.ImportMeta) => {
	const { VITE_COMPRESS_TYPE = "gzip" } = viteEnv;
	return ViteCompression({ algorithm: VITE_COMPRESS_TYPE });
};
