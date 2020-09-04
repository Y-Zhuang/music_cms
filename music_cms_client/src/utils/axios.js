import axios from "axios";
import { getToKen, removeToKen } from "./utils";
import ElementUI from "element-ui";
import router from "@/router";

class HttpRequest {
	request(options) {
		const instance = axios.create();
		options = Object.assign(this.getInsideConfig(), options);
		this.interceptors(instance);
		return instance(options);
	}

	interceptors(instance) {
		instance.interceptors.request.use(
			config => {
				return config;
			},
			error => {
				return Promise.reject(error);
			}
		);
		instance.interceptors.response.use(
			res => {
				if (res.data.code === 440) {
					removeToKen();
					ElementUI.Message({
						message: res.data.message,
						type: "error"
					});
					router.push({ path: "/login" });
					return Promise.reject(res.data.message);
				} else {
					return res.data;
				}
			},
			error => {
				return Promise.reject(error);
			}
		);
	}

	getInsideConfig() {
		return {
			baseURL: "/api",
			headers: { Authorization: getToKen() }
		};
	}
}

export default HttpRequest;
