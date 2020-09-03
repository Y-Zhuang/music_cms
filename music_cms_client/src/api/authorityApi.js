import axios from "./index";

export const getMenuAuthorityApi = data => {
	return axios.request({
		url: "authority/menu/" + data.userId + "/" + data.superiorId,
		method: "get"
	});
};

export const getUserViewByUserIdApi = data => {
	return axios.request({
		url: "authority/view/" + data.userId + "/" + data.superiorId,
		method: "get"
	});
};

export const getUserAuthorityApi = rolesId => {
	return axios.request({
		url: "authority/authority/" + rolesId,
		method: "get"
	});
};

export const getAuthorityApi = () => {
	return axios.request({
		url: "authority/authority/roles",
		method: "get"
	});
};

export const addRolesAuthorityApi = data => {
	return axios.request({
		url: "authority/authority/" + data.rolesId,
		method: "post",
		data: data.data
	});
};

export const getAuthorityAllApi = () => {
	return axios.request({
		url: "authority/authority",
		method: "get"
	});
};
