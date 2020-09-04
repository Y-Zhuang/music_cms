import {
	getMenuAuthorityApi,
	getUserViewByUserIdApi,
	getUserAuthorityApi,
	getAuthorityApi,
	addRolesAuthorityApi,
	getAuthorityAllApi
} from "@/api/authorityApi";

const state = {
	addRoutes: [],
	menuList: []
};
const getters = {
	getMenuList(state) {
		return state.menuList;
	}
};
const mutations = {
	SET_ADD_ROUTERS(state, data) {
		state.addRoutes = data.routes;
		if (data.menuList.isok) {
			state.menuList = data.menuList.data;
		} else {
			state.menuList = [];
		}
	}
};
const actions = {
	getMenuAuthority(state, userId) {
		return getMenuAuthorityApi({ userId: userId, superiorId: -1 });
	},
	getUserMenu(state, userId) {
		return getMenuAuthorityApi({ userId: userId, superiorId: 0 });
	},
	getUserViewByUserId(state, data) {
		return getUserViewByUserIdApi(data);
	},
	getUserAuthority(state, rolesId) {
		return getUserAuthorityApi(rolesId);
	},
	getAuthority() {
		return getAuthorityApi();
	},
	addRolesAuthority(state, data) {
		return addRolesAuthorityApi(data);
	},
	getAuthorityAll() {
		return getAuthorityAllApi();
	}
};

export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
};
