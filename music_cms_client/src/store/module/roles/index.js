import {
	getUserRolesApi,
	addUserRolesApi,
	getRolesAllApi,
	addRolesApi,
	deleteRolesApi,
	getRolesByIdApi,
	updateRolesApi
} from "@/api/rolesApi";

const state = {
	rolesList: [],
	total: 0
};
const getters = {
	getTotal(state) {
		return state.total;
	},
	getRolesList(state) {
		return state.rolesList;
	}
};
const mutations = {
	SET_ROLES_LIST(state, param) {
		state.rolesList = param.list;
		state.total = param.total;
	},
};
const actions = {
	getUserRoles(state, id) {
		return getUserRolesApi(id);
	},
	addUserRoles(state, data) {
		return addUserRolesApi(data);
	},
	getRolesData({ commit }, data) {
		return getRolesAllApi(data).then(res => {
			commit("SET_ROLES_LIST", res.data);
		});
	},
	addRoles(state, formData) {
		return addRolesApi(formData);
	},
	deleteRoles(state, id) {
		return deleteRolesApi(id);
	},
	getRolesDataById(state, id) {
		return getRolesByIdApi(id);
	},
	updateRoles(state, formData) {
		return updateRolesApi(formData);
	}
};
export default {
	namespaced: true,
	state,
	getters,
	mutations,
	actions
};
