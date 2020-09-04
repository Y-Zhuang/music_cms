<template>
	<div class="roles-main">
		<main-header
			:isDisplay="display('addRoles')"
			v-on:centerDialog="openDialog"
			v-on:search="searchData"
		></main-header>
		<el-card class="main-card">
			<el-table :data="rolesList" style="width: 100%" height="670px">
				<el-table-column prop="rolesName" label="角色名" width="450">
				</el-table-column>
				<el-table-column
					prop="explanation"
					label="角色说明"
					width="450"
				>
				</el-table-column>
				<el-table-column prop="createTime" label="创建时间" width="400">
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
							v-if="display('deleteRoles')"
							@click="popoverId = scope.row.id"
							type="text"
							size="small"
						>
							删除
						</el-button>
						<el-button
							v-if="display('editRoles')"
							@click="editRoles(scope.row.id)"
							type="text"
							size="small"
						>
							编辑
						</el-button>
						<el-button
							v-if="display('allotAuthority')"
							@click="addAuthority(scope.row.id)"
							type="text"
							size="small"
						>
							分配权限
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
		<roles-detail
			:centerDialog="centerDialogVisible"
			:rolesId="rolesId"
			:disabled="disabled"
			v-on:clear="clearData"
			v-on:update="updateData"
		></roles-detail>
		<roles-authority
			:rolesId="rolesAuthorityId"
			:visible="rolesVisible"
			:authorityList="authorityList"
			v-on:clear="clearData"
		>
		</roles-authority>
	</div>
</template>

<script>
/**
 * @ComponentName : roles
 * @Description   : 角色管理
 * @Author        : Zhuang
 * @Date          : 2020-04-27 20:36
 */

import { mapGetters, mapActions } from "vuex";
import { getMenuId, isDisplay, initMenu } from "@/utils/utils";
import mainHeader from "@/components/mainHeader";
import mainFooter from "@/components/mainFooter";
import rolesDetail from "./module/rolesDetail";
import rolesAuthority from "./module/rolesAuthority";

export default {
	name: "roles",
	components: {
		mainHeader,
		mainFooter,
		rolesDetail,
		rolesAuthority
	},
	data() {
		return {
			search: "",
			popoverId: "",
			popoverShow: true,
			disabled: false,
			rolesList: [],
			userView: [],
			centerDialogVisible: false,
			rolesVisible: false,
			rolesId: -1,
			rolesAuthorityId: -1,
			authorityList: [],
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
		...mapGetters("authority", ["getMenuList"]),
		...mapGetters("roles", ["getRolesList", "getTotal"]),
		...mapGetters("user", ["getUserId"]),
		...mapActions("authority", ["getUserViewByUserId", "getAuthority"]),
		...mapActions("roles", ["getRolesData", "deleteRoles"]),
		getData(data) {
			this.getRolesData(data).then(() => {
				this.rolesList = this.getRolesList();
				this.pageConf.totalPage = this.getTotal();
			});
		},
		display: function(mark) {
			return isDisplay(this.userView, mark);
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
		clearData() {
			this.rolesId = -1;
			this.rolesAuthorityId = -1;
			this.centerDialogVisible = false;
			this.rolesVisible = false;
		},
		updateData() {
			this.getData({
				pageCode: this.pageConf.pageCode,
				pageSize: this.pageConf.pageSize,
				search: this.search
			});
		},
		openDialog() {
			this.disabled = false;
			this.centerDialogVisible = true;
		},
		deleteData(id) {
			this.deleteRoles(id).then(res => {
				if (res.isok) {
					this.updateData();
					this.$message.success(res.data);
				} else {
					this.$message.error(res.message);
				}
				this.popoverId = "";
			});
		},
		editRoles(id) {
			this.rolesId = id;
			this.disabled = true;
			this.centerDialogVisible = true;
		},
		addAuthority(id) {
			this.rolesAuthorityId = id;
			this.rolesVisible = true;
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
		this.authorityList = initMenu((await this.getAuthority()).data);
	}
};
</script>

<style scoped>
.roles-main {
	margin: 10px 0;
}

.main-card {
	margin: 5px 0;
}
</style>
