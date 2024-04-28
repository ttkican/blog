import { getServiceBaseURL } from "@/utils/service";
import { getToken, token_prefix } from "@/utils/token";
import { createRequest } from "./request";
const isHttpProxy =
	import.meta.env.DEV && import.meta.env.VITE_HTTP_PROXY === "Y";
const { baseURL } = getServiceBaseURL(import.meta.env, isHttpProxy);

export const request = createRequest<App.Service.Response>(
	{
		baseURL,
	},
	{
		async onRequest(config) {
			// 请求带token
			if (getToken()) {
				config.headers["Authorization"] = token_prefix + getToken();
			}

			return config;
		},
		isBackendSuccess(response) {
			return response.data.code === 200;
		},
		async onBackendFail(_response) {

		},
		transformBackendResponse(response) {
			return response.data.data;
		},
		onError(error) {
			let message = error.message;

			if (error.code === "BACKEND_ERROR_CODE") {
				message = error.response?.data?.msg || message;
			}

			window.$message?.error(message);
		},
	}
);
