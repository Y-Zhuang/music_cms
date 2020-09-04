import axios from "./index";

export const addSongApi = data => {
	return axios.request({
		url: "song/song",
		method: "post",
		data
	});
};

export const getSongAllApi = data => {
	return axios.request({
		url: "song/song",
		method: "get",
		params: {
			...data
		}
	});
};

export const getSongByIdApi = id => {
	return axios.request({
		url: "song/song/" + id,
		method: "get"
	});
};

export const uploadApi = data => {
	return axios.request({
		url: "song/upload",
		method: "post",
		data
	});
};

export const getUploadPercentApi = () => {
	return axios.request({
		url: "song/percent",
		method: "get"
	});
};

export const resetPercentApi = () => {
	return axios.request({
		url: "song/reset",
		method: "post"
	});
};

export const updateSongUrlApi = data => {
	return axios.request({
		url: "song/songUrl",
		method: "post",
		data
	});
};

export const updateSongApi = data => {
	return axios.request({
		url: "song/song/" + data.get("id"),
		method: "put",
		data
	});
};

export const deleteSongApi = id => {
	return axios.request({
		url: "song/song/" + id,
		method: "delete"
	});
};

export const getSongApi = () => {
	return axios.request({
		url: "song/song/list",
		method: "get"
	});
};
