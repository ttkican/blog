import axios, {
	AxiosError,
	AxiosResponse,
	CreateAxiosDefaults,
	InternalAxiosRequestConfig,
} from "axios";
import {
	CustomAxiosRequestConfig,
	FlatRequestInstance,
	MappedType,
	RequestOption,
	ResponseType
} from "./type";

function createAxiosConfig(config?: Partial<CreateAxiosDefaults>) {
	const TEN_SECONDS = 10 * 1000;

	const axiosConfig: CreateAxiosDefaults = {
		timeout: TEN_SECONDS,
		headers: {
			"Content-Type": "application/json",
		},
	};

	Object.assign(axiosConfig, config);

	return axiosConfig;
}

function createDefaultOptions<T>(options?: Partial<RequestOption<T>>) {
	const opts: RequestOption<T> = {
		onRequest: async (config) => config,
		isBackendSuccess: (_response) => true,
		onBackendFail: async () => {},
		transformBackendResponse: async (response) => response.data,
		onError: async () => {},
	};

	Object.assign(opts, options);

	return opts;
}

function createCommonRequest<ResponseData = any>(
	axiosConfig?: CreateAxiosDefaults,
	options?: Partial<RequestOption<ResponseData>>
) {
	const opts = createDefaultOptions<ResponseData>(options);

	const axiosConf = createAxiosConfig(axiosConfig);
	const instance = axios.create(axiosConf);

	instance.interceptors.request.use((conf) => {
		const config: InternalAxiosRequestConfig = { ...conf };

		// handle config by hook
		const handledConfig = opts.onRequest?.(config) || config;

		return handledConfig;
	});

	instance.interceptors.response.use(
		async (response) => {
			if (opts.isBackendSuccess(response)) {
				return Promise.resolve(response);
			}

			const fail = await opts.onBackendFail(response, instance);
			if (fail) {
				return fail;
			}

			const backendError = new AxiosError<ResponseData>(
				"the backend request error",
				"BACKEND_ERROR_CODE",
				response.config,
				response.request,
				response
			);

			await opts.onError(backendError);

			return Promise.reject(backendError);
		},
		async (error: AxiosError<ResponseData>) => {
			await opts.onError(error);

			return Promise.reject(error);
		}
	);

	return {
		instance,
		opts,
	};
}

/**
 * create a request instance
 *
 * @param axiosConfig axios config
 * @param options request options
 */
export function createRequest<
	ResponseData = any,
	State = Record<string, unknown>
>(
	axiosConfig?: CreateAxiosDefaults,
	options?: Partial<RequestOption<ResponseData>>
) {
	const { instance, opts} =
		createCommonRequest<ResponseData>(axiosConfig, options);

	const flatRequest: FlatRequestInstance<State, ResponseData> =
		async function flatRequest<T = any, R extends ResponseType = "json">(
			config: CustomAxiosRequestConfig
		) {
			try {
				const response: AxiosResponse<ResponseData> = await instance(config);

				const responseType = response.config?.responseType || "json";

				if (responseType === "json") {
					const data = opts.transformBackendResponse(response);

					return { data, error: null };
				}

				return { data: response.data as MappedType<R, T>, error: null };
			} catch (error) {
				return { data: null, error };
			}
		} as FlatRequestInstance<State, ResponseData>;

	flatRequest.state = {} as State;

	return flatRequest;
}
