<template>
	<el-menu
		class="el-menu-vertical"
		@open="handleOpen"
		:collapse="this.collapse"
		background-color="#344154"
		text-color="#a1a3a6"
		:unique-opened="true"
		:default-active="menu"
		:router="true"
	>
		<el-card class="box-card" :body-style="{ padding: '0px' }">
			<img
				v-if="collapse"
				class="logomin"
				src="../../assets/img/logomin.png"
				alt="logomin"
			/>
			<img
				v-else
				class="logo"
				src="../../assets/img/logo.png"
				alt="logo"
			/>
		</el-card>

		<submenu v-on:itemOpen="itemOpen" :menuData="menuData"></submenu>
	</el-menu>
</template>

<script>
/**
 * @ComponentName : index
 * @Description   : 侧边栏
 * @Author        : Zhuang
 * @Date          : 2020-04-22 18:43
 */

import { mapGetters, mapActions } from "vuex";
import { initMenu } from "@/utils/utils";
import submenu from "./module/submenu";

export default {
	name: "user-Menu",
	components: {
		submenu
	},
	props: {
		collapse: {
			type: Boolean,
			default: false
		},
		menu: {
			type: String,
			defaults: ""
		}
	},
	data() {
		return {
			menuData: []
		};
	},
	async created() {
		this.menuData = initMenu(
			(await this.getUserMenu(this.getUserId())).data
		);
	},
	methods: {
		...mapGetters("user", ["getUserId"]),
		...mapActions("authority", ["getUserMenu"]),
		handleOpen(key) {
			this.$emit("open", key);
		},
		itemOpen(data) {
			this.$emit("itemOpen", data);
		}
	}
};
</script>

<style scoped>
.el-menu-vertical:not(.el-menu--collapse) {
	width: 200px;
}

.box-card {
	height: 60px;
	background-color: #202d3d;
	border: 0;
}

.logo {
	padding-top: 5px;
	height: 55px;
	width: 200px;
}

.logomin {
	padding-top: 2px;
	height: 60px;
	width: 60px;
}
</style>
