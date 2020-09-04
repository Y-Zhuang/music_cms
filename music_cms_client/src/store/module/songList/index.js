import {
	getSongListAllApi,
	addSongListApi,
	getSongListByIdApi,
	getSongIdApi,
	updateSongListApi,
	deleteSongListApi,
	getSongBySongListIdApi
} from "@/api/songListApi";

const state = {
	songLists: [],
	total: 0
};
const getters = {
	getTotal(state) {
		return state.total;
	},
	getSongLists(state) {
		return state.songLists;
	}
};
const mutations = {
	SET_SONG_LISTS(state, param) {
		state.songLists = param.list;
		state.total = param.total;
	}
};
const actions = {
	getSongListData({ commit }, data) {
		return getSongListAllApi(data).then(res => {
			commit("SET_SONG_LISTS", res.data);
		});
	},
	addSongList(state, data) {
		return addSongListApi(data);
	},
	getSongListById(state, id) {
		return getSongListByIdApi(id);
	},
	getSongId(state, id) {
		return getSongIdApi(id);
	},
	updateSongList(state, data) {
		return updateSongListApi(data);
	},
	deleteSongList(state, id) {
		return deleteSongListApi(id);
	},
	getSongBySongListId(state, id) {
		return getSongBySongListIdApi(id);
	}
};
export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
};
