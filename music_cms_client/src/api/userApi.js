import axios from "./index";

export const loginApi = data => {
	return axios.request({
		url: "user/login",
		method: "post",
		data
	});
};

export const getUserByToKenApi = () => {
	return axios.request({
		url: "user/getUserByToKen",
		method: "get"
	});
};

export const getUserAllApi = data => {
	return axios.request({
		url: "user/users",
		method: "get",
		params: {
			...data
		}
	});
};

export const getUserByIdApi = id => {
	return axios.request({
		url: "user/user/" + id,
		method: "get"
	});
};

export const addUserApi = data => {
	return axios.request({
		url: "user/user",
		method: "post",
		data
	});
};

export const deleteUserApi = id => {
	return axios.request({
		url: "user/user/" + id,
		method: "delete"
	});
};

export const updateUserApi = data => {
	return axios.request({
		url: "user/user/" + data.get("id"),
		method: "put",
		data
	});
};
