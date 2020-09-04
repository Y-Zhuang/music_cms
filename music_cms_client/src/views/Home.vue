<template>
	<el-container class="content">
		<el-aside class="Aside" width="60px;">
			<user-menu
				v-on:open="handleOpen"
				v-on:itemOpen="itemOpen"
				:collapse="this.isCollapse"
				:menu="menu"
			></user-menu>
		</el-aside>
		<el-container>
			<el-header class="Header">
				<user-header
					:tabs="tabs"
					:userName="userName"
					v-on:listenHeader="eventHeader"
				></user-header>
			</el-header>
			<el-main class="Main">
				<m-tag
					:tags="tags"
					v-on:clickTag="clickTag"
					v-on:closeTag="closeTag"
				></m-tag>
				<router-view></router-view>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import userHeader from "@/components/header";
import userMenu from "@/components/menu";
import mTag from "@/components/tag";

export default {
	name: "Home",
	components: {
		userHeader,
		userMenu,
		mTag
	},
	data() {
		return {
			isCollapse: false,
			tabs: "首页",
			userName: "",
			tags: [],
			menu: ""
		};
	},
	async created() {
		this.userName = this.getUserName();
		let url = this.$router.history.current.name;
		if (url !== "Home") {
			let data = await this.getMenuAuthority(this.getUserId());
			if (data.isok) {
				data.data.forEach(item => {
					if (item.mark === url) {
						this.tags = [
							{
								name: item.authorityName,
								mark: item.mark,
								superiorId: item.superiorId
							}
						];
					}
				});
			}
		}
	},
	methods: {
		...mapGetters("user", ["getUserId", "getUserName"]),
		...mapActions("authority", ["getMenuAuthority"]),
		eventHeader(data) {
			this.isCollapse = data;
		},
		handleOpen(data) {
			this.tabs = data;
		},
		itemOpen(data) {
			this.menu = data.mark;
			if (this.tags.length === 0) {
				this.tags = [data];
			} else {
				if (this.tags[0].superiorId === data.superiorId) {
					let flag = true;
					this.tags.forEach(item => {
						if (data.mark === item.mark) {
							flag = false;
						}
					});
					if (flag) {
						this.tags.push(data);
					}
				} else {
					this.tags = [data];
				}
			}
		},
		clickTag(index) {
			let mark = this.tags[index].mark;
			this.menu = mark;
			this.$router.push({ name: mark });
		},
		closeTag(index) {
			if (this.tags.length === 1) {
				this.tags = [];
				this.$router.push({ name: "Home" });
			} else {
				this.tags.splice(index, 1);
				let mark = this.tags[this.tags.length - 1].mark;
				this.menu = mark;
				this.$router.push({ name: mark });
			}
		}
	}
};
</script>

<style>
.content {
	width: 100vw;
	height: 100vh;
}

.Header {
	background-color: #eef1f6;
}

.Aside {
	background-color: #344154;
}

.Main {
	padding: 5px 10px;
}
</style>
