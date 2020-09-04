import Login from "@/views/Login";

export default [
	{
		path: "/",
		name: "Login",
		component: Login
	},
	{
		path: "/login",
		name: "Login",
		component: Login
	},
	{
		path: "/home",
		name: "Home",
		component: () => import("@/views/Home")
	}
];

export const asyncRoutes = {
	User: {
		path: "/user",
		name: "User",
		component: () => import("@/components/user")
	},
	Roles: {
		path: "/roles",
		name: "Roles",
		component: () => import("@/components/roles")
	},
	Authority: {
		path: "/authority",
		name: "Authority",
		component: () => import("@/components/authority")
	},
	Song: {
		path: "/song",
		name: "Song",
		component: () => import("@/components/song")
	},
	songList: {
		path: "/songList",
		name: "songList",
		component: () => import("@/components/songList")
	}
};
