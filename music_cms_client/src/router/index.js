import Vue from "vue";
import store from "@/store";
import VueRouter from "vue-router";
import routes from "./routes";
import { getToKen, initRoutes } from "@/utils/utils";

Vue.use(VueRouter);

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes
});

//eslint-disable-next-line no-unused-vars
router.beforeEach(async (to, from, next) => {
	if (getToKen()) {
		try {
			if (!store.getters.getUserName) {
				await store.dispatch("user/getUser");
			}
			if (to.name === "Login") {
				next({ name: "Home" });
			} else {
				if (store.getters.getAddRoutes.length === 0) {
					let menuList = await store.dispatch(
						"authority/getMenuAuthority",
						store.getters.getUserId
					);
					let Routes = initRoutes(menuList);
					router.addRoutes(Routes);
					store.commit("authority/SET_ADD_ROUTERS", {
						routes: Routes,
						menuList: menuList
					});
					next({ path: to.path });
				}
				next();
			}
		} catch (error) {
			store.commit("user/RESET_USER_TOKEN");
			next({ name: "Login" });
		}
	} else {
		if (to.name === "Login") next();
		else next({ name: "Login" });
	}
});

export default router;
