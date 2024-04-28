declare namespace Env {

	interface ImportMeta extends ImportMetaEnv {
		/** 后台服务地址 */
		readonly VITE_SERVICE_BASE_URL: string;
		/** iconify图标作为组件的前缀 */
		readonly VITE_ICON_PREFIX?: string;
		/**
		 * 本地SVG图标作为组件的前缀, 请注意一定要包含 VITE_ICON_PREFIX
		 * - 格式 {VITE_ICON_PREFIX}-{本地图标集合名称}
		 * - 例如：icon-local
		 */
		readonly VITE_ICON_LOCAL_PREFIX: string;
		/** 开启请求代理 */
		readonly VITE_HTTP_PROXY?: CommonType.YesOrNo;
		/** 打包文件名称 */
		readonly VITE_DIST_NAME?: string;
		/** 是否开启打包文件大小结果分析 */
		readonly VITE_VISUALIZER?: CommonType.YesOrNo;
		/** 是否开启打包压缩 */
		readonly VITE_COMPRESS?: CommonType.YesOrNo;
		/** 压缩算法类型 */
		readonly VITE_COMPRESS_TYPE?:
			| "gzip"
			| "brotliCompress"
			| "deflate"
			| "deflateRaw";
	}

}
