<template>
	<el-dialog
		:title="getTitle"
		:visible="centerDialogVisible"
		@close="closeDialog"
		width="60%"
		center
	>
		<el-form
			:model="newRoles"
			:rules="rules"
			ref="ruleForm"
			label-width="100px"
			class="demo-ruleForm"
		>
			<el-form-item label="角色名" prop="rolesName">
				<el-input
					placeholder="请输入角色名"
					v-model.trim="newRoles.rolesName"
				></el-input>
			</el-form-item>
			<el-form-item label="角色说明" prop="explanation">
				<el-input
					placeholder="请输入角色说明"
					v-model.trim="newRoles.explanation"
				></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer">
			<el-button @click="resetForm()">重 置</el-button>
			<el-button type="primary" @click="submitData()" :loading="logining">
				提 交
			</el-button>
		</span>
	</el-dialog>
</template>

<script>
/**
 * @ComponentName : rolesDetail
 * @Description   : 角色详情组件
 * @Author        : Zhuang
 * @Date          : 2020-05-03 0:58
 */

import { mapActions } from "vuex";

export default {
	name: "rolesDetail",
	props: {
		disabled: {
			type: Boolean,
			required: true,
			defaults: false
		},
		centerDialog: {
			type: Boolean,
			required: true,
			defaults: false
		},
		rolesId: {
			type: Number,
			required: true,
			defaults: -1
		}
	},
	computed: {
		getTitle: function() {
			return this.disabled ? "编辑角色" : "添加角色";
		}
	},
	data() {
		return {
			logining: false,
			centerDialogVisible: false,
			newRoles: {
				rolesName: "",
				explanation: ""
			},
			rules: {
				rolesName: [
					{
						required: true,
						message: "角色名不许为空",
						trigger: "blur"
					}
				],
				explanation: [
					{
						required: true,
						message: "角色说明不许为空",
						trigger: "blur"
					}
				]
			}
		};
	},
	methods: {
		...mapActions("roles", ["addRoles", "getRolesDataById", "updateRoles"]),
		closeDialog() {
			this.$refs.ruleForm.resetFields();
			this.$emit("clear");
		},
		resetForm() {
			this.$refs.ruleForm.resetFields();
			if (this.disabled && this.centerDialogVisible) {
				this.getRolesDataById(this.newRoles.id).then(res => {
					this.newRoles = res.data;
				});
			}
		},
		submitData() {
			this.$refs.ruleForm.validate(valid => {
				if (valid) {
					this.logining = true;
					let formData = new FormData();
					Object.keys(this.newRoles).forEach(key => {
						formData.append(key, this.newRoles[key]);
					});
					formData.delete("createTime");
					if (this.disabled) {
						this.updateRolesData(formData);
					} else {
						this.addRolesData(formData);
					}
				}
			});
		},
		addRolesData(formData) {
			this.addRoles(formData).then(res => {
				if (res.isok) {
					this.$emit("update");
					this.$message.success(res.data);
					this.centerDialogVisible = false;
				} else {
					this.$message.error(res.message);
				}
				this.logining = false;
			});
		},
		updateRolesData(formData) {
			this.updateRoles(formData).then(res => {
				if (res.isok) {
					this.$emit("update");
					this.$message.success(res.data);
					this.centerDialogVisible = false;
				} else {
					this.$message.error(res.message);
				}
				this.logining = false;
			});
		}
	},
	watch: {
		rolesId: {
			immediate: true,
			handler(val) {
				if (this.disabled && val !== -1) {
					this.getRolesDataById(val).then(res => {
						this.newRoles = res.data;
					});
				}
			}
		},
		centerDialog: {
			immediate: true,
			handler(val) {
				this.centerDialogVisible = val;
			}
		}
	}
};
</script>

<style scoped>

</style>
