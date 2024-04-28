/**
 * 获取当前环境模式下的请求服务的配置
 * @param env 环境
 */
export function createServiceConfig(env: Env.ImportMeta) {
	const { VITE_SERVICE_BASE_URL } = env;

	const config: App.Service.ServiceConfig = {
		baseURL: VITE_SERVICE_BASE_URL,
		proxyPattern: createProxyPattern(),
	};

	return config;
}

export function getServiceBaseURL(env: Env.ImportMeta, isHttpProxy: boolean) {
	const { baseURL } = createServiceConfig(env);
	return {
		baseURL: isHttpProxy ? createProxyPattern() : baseURL,
	};
}

function createProxyPattern() {
	return "/api";
}
