import Vue from "vue";
import Vuex from "vuex";
import user from "./module/user";
import roles from "./module/roles";
import authority from "./module/authority";
import song from "./module/song";
import songList from "./module/songList";

Vue.use(Vuex);

export default new Vuex.Store({
	state: {},
	getters: {
		getAddRoutes: state => {
			return state.authority.addRoutes;
		},
		getUserId: state => {
			return state.user.user.userId;
		},
		getUserName: state => {
			return state.user.user.userName;
		}
	},
	mutations: {},
	actions: {},
	modules: {
		user,
		roles,
		authority,
		song,
		songList
	}
});
