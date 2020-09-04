import {
	addSongApi,
	getSongAllApi,
	uploadApi,
	getSongByIdApi,
	getUploadPercentApi,
	resetPercentApi,
	updateSongUrlApi,
	updateSongApi,
	deleteSongApi,
	getSongApi
} from "@/api/songApi";

const state = {
	songList: [],
	total: 0
};
const getters = {
	getTotal(state) {
		return state.total;
	},
	getSongList(state) {
		return state.songList;
	}
};
const mutations = {
	SET_SONG_LIST(state, param) {
		state.songList = param.list;
		state.total = param.total;
	}
};
const actions = {
	addSong(state, formData) {
		return addSongApi(formData);
	},
	getSongData({ commit }, data) {
		return getSongAllApi(data).then(res => {
			commit("SET_SONG_LIST", res.data);
		});
	},
	uploadData(state, data) {
		return uploadApi(data);
	},
	getSongById(state, id) {
		return getSongByIdApi(id);
	},
	getUploadPercent() {
		return getUploadPercentApi();
	},
	resetPercent() {
		return resetPercentApi();
	},
	updateSongUrl(state, data) {
		return updateSongUrlApi(data);
	},
	updateSong(state, data) {
		return updateSongApi(data);
	},
	deleteSong(state, id) {
		return deleteSongApi(id);
	},
	getSong() {
		return getSongApi();
	}
};
export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
};
