/**
 *后台服务的环境类型
 * - dev: 后台开发环境
 * - test: 后台测试环境
 * - prod: 后台生产环境
 */
type ServiceEnvType = "dev" | "test" | "prod";
/** 后台服务环境配置 */
interface ServiceEnvConfig {
  /** 请求地址 */
  url: string;
}

interface ServiceEnvConfigWithProxyPattern extends ServiceEnvConfig {
  /**
   * 匹配路径的正则字符串
   * - 用于拦截地址转发代理(任意以 /开头 + 字符串, 单个/不起作用)
   * - 和后端请求地址的前缀无关
   * - 有多个后端请求实例时，需要创建不同的值
   */
  proxyPattern: "/api";
}

interface ImportMetaEnv {
	/** 后端服务的环境类型 */
	readonly VITE_SERVICE_ENV?: ServiceEnvType;
	/** iconify图标作为组件的前缀 */
	readonly VITE_ICON_PREFIX: string;
	/**
	 * 本地SVG图标作为组件的前缀, 请注意一定要包含 VITE_ICON_PREFIX
	 * - 格式 {VITE_ICON_PREFIX}-{本地图标集合名称}
	 * - 例如：icon-local
	 */
	readonly VITE_ICON_LOCAL_PREFIX: string;
	/** 开启请求代理 */
	readonly VITE_HTTP_PROXY?: "Y" | "N";
	/** 打包文件名称 */
	readonly VITE_DIST_NAME: string;
	/** 是否开启打包文件大小结果分析 */
	readonly VITE_VISUALIZER?: "Y" | "N";
	/** 是否开启打包压缩 */
	readonly VITE_COMPRESS?: "Y" | "N";
	/** 压缩算法类型 */
	readonly VITE_COMPRESS_TYPE?:
		| "gzip"
		| "brotliCompress"
		| "deflate"
		| "deflateRaw";
}
