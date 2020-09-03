import { asyncRoutes } from "@/router/routes";

export const setToKen = token => {
	sessionStorage.setItem("token", token);
};

export const getToKen = () => {
	return sessionStorage.getItem("token");
};

export const removeToKen = () => {
	sessionStorage.clear();
};

export const initRoutes = data => {
	let home = [
		{
			path: "/home",
			name: "Home",
			component: () => import("@/views/Home"),
			children: ""
		}
	];
	let children = [];
	if (data.isok) {
		data.data.forEach(item => {
			children.push(asyncRoutes[item.mark]);
		});
	}
	home[0].children = children;
	home.push({
		path: "/*",
		component: () => import("@/views/Home")
	});
	return home;
};

export const initMenu = menuList => {
	if (!menuList.length) return [];
	const handle = id => {
		let list = [];
		menuList.forEach(item => {
			if (item.superiorId === id) {
				item.children = handle(item.id);
				list.push(item);
			}
		});
		return list;
	};
	return handle(0);
};

export const getMenuId = (menuList, mark) => {
	if (!menuList.length) return null;
	let menuId = "";
	menuList.forEach(item => {
		if (item.mark === mark) {
			menuId = item.id;
		}
	});
	return menuId;
};

export const isDisplay = (viewList, mark) => {
	if (!viewList.length) return false;
	let isDisplay = false;
	viewList.forEach(item => {
		if (item.mark === mark) {
			isDisplay = true;
		}
	});
	return isDisplay;
};

export const initSong = songList => {
	if (!songList.length) return [];
	const data = [];
	songList.forEach(item => {
		data.push({
			label: item.songName,
			key: item.id
		});
	});
	return data;
};
