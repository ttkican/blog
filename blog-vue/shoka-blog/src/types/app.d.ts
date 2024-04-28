declare namespace App {
	namespace Service {

		interface ServiceConfig {
			/** The backend service base url */
			baseURL: string;
			/** The proxy pattern of the backend service base url */
			proxyPattern: string;
		}

		/** The backend service response data */
		type Response<T = unknown> = {
			/**
			 * 返回状态
			 */
			flag: boolean;
			/**
			 * 状态码
			 */
			code: number;
			/**
			 * 返回信息
			 */
			msg: string;
			/**
			 * 返回数据
			 */
			data: T;
		};
	}
}
