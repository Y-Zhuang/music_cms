<template>
	<el-dialog
		title="分配角色"
		:visible="centerDialogVisible"
		@close="closeDialog"
		width="50%"
	>
		<div class="roles-dig">
			<el-tag
				v-for="(item, index) in userRoles"
				:key="item.id"
				class="roles-tag"
				type="warning"
				effect="dark"
				:disable-transitions="true"
			>
				{{ item.rolesName }}
				<i
					class="el-icon-remove-outline roles-icon"
					@click="cutRoles(index)"
				></i>
			</el-tag>
		</div>
		<div class="roles-dig roles-not">
			<el-tag
				v-for="(item, index) in userNotRoles"
				:key="item.id"
				class="roles-tag"
				type="info"
				effect="dark"
				:disable-transitions="true"
			>
				{{ item.rolesName }}
				<i
					class="el-icon-circle-plus-outline roles-icon"
					@click="putRoles(index)"
				></i>
			</el-tag>
		</div>
		<span slot="footer" class="dialog-footer">
			<el-button @click="closeDialog">取 消</el-button>
			<el-button type="primary" @click="commitRoles">
				确 定
			</el-button>
		</span>
	</el-dialog>
</template>

<script>
/**
 * @ComponentName : userRoles
 * @Description   : 用户角色组件
 * @Author        : Zhuang
 * @Date          : 2020-04-30 3:47
 */

import { mapActions } from "vuex";

export default {
	name: "userRoles",
	props: {
		visible: {
			type: Boolean,
			required: true,
			defaults: false
		},
		userId: {
			type: Number,
			required: true,
			defaults: -1
		}
	},
	data() {
		return {
			centerDialogVisible: false,
			userRoles: [
				{
					id: "",
					rolesName: ""
				}
			],
			userNotRoles: [
				{
					id: "",
					rolesName: ""
				}
			],
			putRolesId: [],
			cutRolesId: []
		};
	},
	methods: {
		...mapActions("roles", ["getUserRoles", "addUserRoles"]),
		closeDialog() {
			this.putRolesId = [];
			this.cutRolesId = [];
			this.userRoles = [];
			this.userNotRoles = [];
			this.$emit("clear");
		},
		cutRoles(index) {
			let roles = this.userRoles.splice(index, 1)[0];
			this.userNotRoles.push(roles);
			this.cutRolesId.push(roles.id);
			let idIndex = this.putRolesId.indexOf(roles.id);
			if (idIndex !== -1) {
				this.putRolesId.splice(idIndex, 1);
			}
		},
		putRoles(index) {
			let roles = this.userNotRoles.splice(index, 1)[0];
			this.userRoles.push(roles);
			this.putRolesId.push(roles.id);
			let idIndex = this.cutRolesId.indexOf(roles.id);
			if (idIndex !== -1) {
				this.cutRolesId.splice(idIndex, 1);
			}
		},
		commitRoles() {
			let fromData = new FormData();
			fromData.append("putRolesId", this.putRolesId);
			fromData.append("cutRolesId", this.cutRolesId);
			this.addUserRoles({
				userId: this.userId,
				data: fromData
			}).then(res => {
				if (res.isok) {
					this.$message.success(res.data);
					this.closeDialog();
				} else {
					this.$message.warning(res.message);
				}
			});
		}
	},
	watch: {
		userId: {
			immediate: true,
			handler(val) {
				if (val !== -1) {
					this.getUserRoles(val).then(res => {
						if (res.isok) {
							this.userRoles = res.data.userRoles;
							this.userNotRoles = res.data.userNotRoles;
						}
					});
				}
			}
		},
		visible: {
			immediate: true,
			handler(val) {
				this.centerDialogVisible = val;
			}
		}
	}
};
</script>

<style scoped>
.roles-dig {
	border-bottom: 2px solid #cac6c6;
	padding: 10px;
}

.roles-not {
	border: 0;
}

.roles-tag {
	margin: 5px;
}

.roles-icon {
	cursor: pointer;
}
</style>
