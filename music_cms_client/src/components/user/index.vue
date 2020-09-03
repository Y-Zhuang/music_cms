<template>
	<div class="user-main">
		<main-header
			:isDisplay="display('addUser')"
			v-on:centerDialog="openDialog"
			v-on:search="searchData"
		></main-header>
		<el-card class="main-card">
			<el-table :data="userList" style="width: 100%" height="670px">
				<el-table-column prop="userName" label="姓名" width="200">
				</el-table-column>
				<el-table-column label="性别" width="200">
					<template slot-scope="userList">
						{{ userList.row.sex | formatSex }}
					</template>
				</el-table-column>
				<el-table-column prop="age" label="年龄" width="200">
				</el-table-column>
				<el-table-column prop="phoneNumber" label="手机号" width="300">
				</el-table-column>
				<el-table-column prop="password" label="密码" width="200">
				</el-table-column>
				<el-table-column prop="createTime" label="创建时间" width="200">
				</el-table-column>
				<el-table-column fixed="right" label="操作" width="200">
					<template slot-scope="scope">
						<el-popover
							placement="top"
							width="160"
							v-model="popoverShow"
							v-show="popoverId === scope.row.id"
						>
							<p>确定删除吗？</p>
							<div style="text-align: right; margin: 0">
								<el-button
									size="mini"
									type="text"
									@click="popoverId = ''"
								>
									取消
								</el-button>
								<el-button
									type="primary"
									size="mini"
									@click="deleteData(scope.row.id)"
								>
									确定
								</el-button>
							</div>
						</el-popover>
						<el-button
							v-if="display('deleteUser')"
							@click="popoverId = scope.row.id"
							type="text"
							size="small"
						>
							删除
						</el-button>
						<el-button
							v-if="display('editUser')"
							@click="editUser(scope.row.id)"
							type="text"
							size="small"
						>
							编辑
						</el-button>
						<el-button
							v-if="display('allotRoles')"
							@click="addRoles(scope.row.id)"
							type="text"
							size="small"
						>
							分配角色
						</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<main-footer
			v-on:currentChange="currentChange"
			:totalPage="pageConf.totalPage"
			:pageSize="pageConf.pageSize"
		></main-footer>
		<user-detail
			:centerDialog="centerDialogVisible"
			:userId="userId"
			:disabled="disabled"
			v-on:clear="clearData"
			v-on:update="updateData"
		></user-detail>
		<user-roles
			:visible="rolesVisible"
			v-on:clear="clearData"
			:userId="rolesUserId"
		></user-roles>
	</div>
</template>

<script>
/**
 * @ComponentName : user
 * @Description   : 用户列表
 * @Author        : Zhuang
 * @Date          : 2020-04-23 16:23
 */

import { mapGetters, mapActions } from "vuex";
import { getMenuId, isDisplay } from "@/utils/utils";
import mainHeader from "@/components/mainHeader";
import mainFooter from "@/components/mainFooter";
import userDetail from "./module/userDetail";
import userRoles from "./module/userRoles";

export default {
	name: "user",
	components: {
		mainHeader,
		mainFooter,
		userDetail,
		userRoles
	},
	data() {
		return {
			search: "",
			popoverId: "",
			popoverShow: true,
			disabled: false,
			userList: [],
			userView: [],
			centerDialogVisible: false,
			rolesVisible: false,
			userId: -1,
			rolesUserId: -1,
			pageConf: {
				//当前页
				pageCode: 1,
				//一页显示条数
				pageSize: 11,
				//总记录数
				totalPage: 12
			}
		};
	},
	methods: {
		...mapGetters("user", ["getUserList", "getTotal", "getUserId"]),
		...mapGetters("authority", ["getMenuList"]),
		...mapActions("user", ["getUserData", "deleteUser"]),
		...mapActions("authority", ["getUserViewByUserId"]),
		deleteData(id) {
			this.deleteUser(id).then(res => {
				if (res.isok) {
					this.updateData();
					this.$message.success(res.data);
				} else {
					this.$message.error(res.message);
				}
				this.popoverId = "";
			});
		},
		editUser(id) {
			this.userId = id;
			this.disabled = true;
			this.centerDialogVisible = true;
		},
		getData(data) {
			this.getUserData(data).then(() => {
				this.userList = this.getUserList();
				this.pageConf.totalPage = this.getTotal();
			});
		},
		clearData() {
			this.userId = -1;
			this.rolesUserId = -1;
			this.centerDialogVisible = false;
			this.rolesVisible = false;
		},
		openDialog() {
			this.disabled = false;
			this.centerDialogVisible = true;
		},
		currentChange(pageCode, pageSize) {
			this.pageConf.pageCode = pageCode;
			this.pageConf.pageSize = pageSize;
			this.getData({
				pageCode: pageCode,
				pageSize: pageSize,
				search: this.search
			});
		},
		updateData() {
			this.getData({
				pageCode: this.pageConf.pageCode,
				pageSize: this.pageConf.pageSize,
				search: this.search
			});
		},
		addRoles(id) {
			this.rolesUserId = id;
			this.rolesVisible = true;
		},
		display: function(mark) {
			return isDisplay(this.userView, mark);
		},
		searchData(val) {
			this.search = val;
			this.updateData();
		}
	},
	async created() {
		this.getData({
			pageCode: this.pageConf.pageCode,
			pageSize: this.pageConf.pageSize,
			search: this.search
		});
		let menuId = getMenuId(
			this.getMenuList(),
			this.$router.history.current.name
		);
		if (menuId !== null && menuId !== "") {
			this.userView = (
				await this.getUserViewByUserId({
					userId: this.getUserId(),
					superiorId: menuId
				})
			).data;
		}
	},
	filters: {
		formatSex(sex) {
			if (sex === 0) {
				return "男";
			} else {
				return "女";
			}
		}
	}
};
</script>

<style scoped>
.user-main {
	margin: 10px 0;
}

.main-card {
	margin: 5px 0;
}
</style>
