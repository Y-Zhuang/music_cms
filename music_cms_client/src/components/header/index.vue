<template>
	<div style="text-align: right; font-size: 15px">
		<el-row>
			<el-col :span="12">
				<div class="grid-content bg-purple">
					<i
						:class="[
							'my-icon-zhankai2',
							{ open: !collapse, close: collapse }
						]"
						@click="isCollapse()"
					></i>
					<span class="item-title">{{ tabs }}</span>
				</div>
			</el-col>
			<el-col :span="12">
				<div class="grid-content bg-purple-light">
					<el-dropdown @command="signOut()" class="user-header">
						<i
							class="el-icon-setting"
							style="margin-right: 10px"
						></i>
						<el-dropdown-menu slot="dropdown">
							<el-dropdown-item>
								退出登录
							</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
					<span>{{ userName }}</span>
				</div>
			</el-col>
		</el-row>
	</div>
</template>

<script>
/**
 * @ComponentName : index
 * @Description   : 头部组件
 * @Author        : Zhuang
 * @Date          : 2020-04-22 18:17
 */

import { mapMutations } from "vuex";

export default {
	name: "user-header",
	props: {
		tabs: {
			type: String,
			required: true,
			default: "首页"
		},
		userName: {
			type: String,
			required: true
		}
	},
	data() {
		return {
			collapse: false,
		};
	},
	methods: {
		...mapMutations("user", ["RESET_USER_TOKEN"]),
		isCollapse() {
			this.collapse = !this.collapse;
			this.$emit("listenHeader", this.collapse);
		},
		signOut() {
			this.RESET_USER_TOKEN();
			this.$router.push({ name: "Login" });
		}
	}
};
</script>

<style scoped>
.user-header {
	line-height: 60px;
}

.item-title {
	float: left;
	font-size: 15px;
	font-weight: bold;
	line-height: 59px;
	margin-left: 20px;
	color: #464547;
}

.my-icon-zhankai2 {
	float: left;
	font-size: 1.5rem;
	line-height: 60px;
	color: #2b3e55;
}

.open {
	transform: rotate(180deg);
}

.close {
	transform: rotate(0deg);
}
</style>
