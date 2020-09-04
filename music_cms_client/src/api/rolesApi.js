import axios from "./index";

export const getUserRolesApi = id => {
	return axios.request({
		url: "roles/roles/user/" + id,
		method: "get"
	});
};

export const addUserRolesApi = data => {
	return axios.request({
		url: "roles/roles/" + data.userId,
		method: "post",
		data: data.data
	});
};

export const getRolesAllApi = date => {
	return axios.request({
		url: "roles/roles",
		method: "get",
		params: {
			...date
		}
	});
};

export const deleteRolesApi = id => {
	return axios.request({
		url: "roles/roles/" + id,
		method: "delete"
	});
};

export const updateRolesApi = data => {
	return axios.request({
		url: "roles/roles/" + data.get("id"),
		method: "put",
		data
	});
};

export const getRolesByIdApi = id => {
	return axios.request({
		url: "roles/roles/" + id,
		method: "get"
	});
};

export const addRolesApi = data => {
	return axios.request({
		url: "roles/roles",
		method: "post",
		data
	});
};
