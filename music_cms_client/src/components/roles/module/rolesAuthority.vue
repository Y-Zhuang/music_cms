<template>
	<el-dialog
		title="分配权限"
		:visible="centerDialogVisible"
		@close="closeDialog"
		width="50%"
	>
		<el-tree
			:data="authorityList"
			show-checkbox
			default-expand-all
			node-key="id"
			ref="tree"
			:default-checked-keys="userAuthority"
			:props="authorityProps"
			:render-content="renderContent"
			:check-strictly="true"
		>
		</el-tree>
		<span slot="footer" class="dialog-footer">
			<el-button @click="closeDialog">取 消</el-button>
			<el-button type="primary" @click="commitAuthority">
				确 定
			</el-button>
		</span>
	</el-dialog>
</template>

<script>
/**
 * @ComponentName : rolesAuthority
 * @Description   : 角色权限组件
 * @Author        : Zhuang
 * @Date          : 2020-05-03 2:51
 */

import { mapActions } from "vuex";

export default {
	name: "rolesAuthority",
	props: {
		visible: {
			type: Boolean,
			required: true,
			defaults: false
		},
		rolesId: {
			type: Number,
			required: true,
			defaults: -1
		},
		authorityList: {
			type: Array,
			defaults: () => []
		}
	},
	data() {
		return {
			centerDialogVisible: false,
			userAuthority: [],
			authorityProps: {
				children: "children",
				label: "authorityName"
			}
		};
	},
	methods: {
		...mapActions("authority", ["getUserAuthority", "addRolesAuthority"]),
		closeDialog() {
			this.$refs.tree.setCheckedKeys([]);
			this.userAuthority = [];
			this.$emit("clear");
		},
		commitAuthority() {
			if (this.$refs.tree.getCheckedKeys().length) {
				let formData = new FormData();
				formData.append("authorityId", this.$refs.tree.getCheckedKeys());
				this.addRolesAuthority({
					rolesId: this.rolesId,
					data: formData
				}).then(res => {
					if (res.isok) {
						this.$message.success(res.data);
						this.closeDialog();
					} else {
						this.$message.error(res.message);
					}
				});
			} else {
				this.$message.warning("您还未分配权限");
			};
		},
		renderContent(h, { node, data }) {
			return (
				<span>
					<i class={data.icon}></i>
					<span style="padding-left: 5px;">{node.label}</span>
				</span>
			);
		}
	},
	watch: {
		rolesId: {
			immediate: true,
			handler(val) {
				if (val !== -1) {
					this.getUserAuthority(val).then(res => {
						if (res.isok) {
							this.userAuthority = res.data;
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

<style scoped></style>
