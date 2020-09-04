import { removeToKen } from "@/utils/utils";
import {
	loginApi,
	getUserByToKenApi,
	getUserAllApi,
	addUserApi,
	deleteUserApi,
	getUserByIdApi,
	updateUserApi
} from "@/api/userApi";

const state = {
	user: {
		userId: "",
		userName: ""
	},
	userList: [],
	total: 0
};
const getters = {
	getUserId(state) {
		return state.user.userId;
	},
	getUserName(state) {
		return state.user.userName;
	},
	getTotal(state) {
		return state.total;
	},
	getUserList(state) {
		return state.userList;
	}
};
const mutations = {
	SET_USER(state, param) {
		state.user.userId = param.id;
		state.user.userName = param.userName;
	},
	SET_USER_LIST(state, param) {
		state.userList = param.list;
		state.total = param.total;
	},
	RESET_USER_TOKEN(state) {
		state.user.userId = "";
		state.user.userName = "";
		removeToKen();
	}
};
const actions = {
	userLogin(state, formData) {
		return loginApi(formData);
	},
	getUser({ commit }) {
		return new Promise((resolve, reject) => {
			getUserByToKenApi().then(res => {
				if (res.isok) {
					commit("SET_USER", res.data);
					resolve();
				} else {
					reject();
				}
			});
		});
	},
	getUserData({ commit }, data) {
		return getUserAllApi(data).then(res => {
			commit("SET_USER_LIST", res.data);
		});
	},
	addUser(state, formData) {
		return addUserApi(formData);
	},
	deleteUser(state, id) {
		return deleteUserApi(id);
	},
	getUserDataById(state, id) {
		return getUserByIdApi(id);
	},
	updateUser(state, formData) {
		return updateUserApi(formData);
	}
};

export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
};
