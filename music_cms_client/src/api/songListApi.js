import axios from "./index";

export const getSongListAllApi = data => {
	return axios.request({
		url: "songList/songList",
		method: "get",
		params: {
			...data
		}
	});
};

export const addSongListApi = data => {
	return axios.request({
		url: "songList/songList",
		method: "post",
		data
	});
};

export const getSongListByIdApi = id => {
	return axios.request({
		url: "songList/songList/" + id,
		method: "get"
	});
};

export const getSongIdApi = id => {
	return axios.request({
		url: "songList/songList/id/" + id,
		method: "get"
	});
};

export const updateSongListApi = data => {
	return axios.request({
		url: "songList/songList/" + data.get("id"),
		method: "put",
		data
	});
};

export const deleteSongListApi = id => {
	return axios.request({
		url: "songList/songList/" + id,
		method: "delete"
	});
};

export const getSongBySongListIdApi = id => {
	return axios.request({
		url: "songList/songList/song/" + id,
		method: "get"
	});
}
