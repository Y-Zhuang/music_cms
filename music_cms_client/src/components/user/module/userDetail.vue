<template>
	<el-dialog
		:title="getTitle"
		:visible="centerDialogVisible"
		@close="closeDialog"
		width="60%"
		center
	>
		<el-form
			:model="newUser"
			:rules="rules"
			ref="ruleForm"
			label-width="100px"
			class="demo-ruleForm"
		>
			<el-form-item label="用户名" prop="userName">
				<el-input
					placeholder="请输入用户名"
					v-model.trim="newUser.userName"
				></el-input>
			</el-form-item>
			<el-form-item label="密码" prop="password">
				<el-input
					placeholder="请输入密码"
					v-model.trim="newUser.password"
				></el-input>
			</el-form-item>
			<el-form-item label="手机号" prop="phoneNumber">
				<el-input
					placeholder="请输入手机号"
					type="age"
					:disabled="disabled"
					v-model.trim="newUser.phoneNumber"
				></el-input>
			</el-form-item>
			<el-form-item label="性别" prop="sex">
				<el-radio-group v-model="newUser.sex">
					<el-radio :label="0">男</el-radio>
					<el-radio :label="1">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="年龄" prop="age">
				<el-input-number
					v-model.trim="newUser.age"
					:min="1"
					:max="200"
				></el-input-number>
			</el-form-item>
			<div class="images-upload">
				<el-upload
					class="avatar-uploader"
					action=""
					:auto-upload="false"
					:show-file-list="false"
					:on-change="changeUpload"
				>
					<img
						v-if="imgUrl"
						:src="imgUrl"
						class="avatar"
						alt=""
					/>
					<i v-else class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
			</div>
		</el-form>
		<span slot="footer">
			<el-button @click="resetData()">重 置</el-button>
			<el-button type="primary" @click="submitData()" :loading="logining">
				提 交
			</el-button>
		</span>
	</el-dialog>
</template>

<script>
/**
 * @ComponentName : userDetail
 * @Description   : 用户详情组件
 * @Author        : Zhuang
 * @Date          : 2020-04-24 15:17
 */

import { mapActions } from "vuex";

export default {
	name: "userDetail",
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
		userId: {
			type: Number,
			required: true,
			defaults: -1
		}
	},
	computed: {
		getTitle: function() {
			return this.disabled ? "编辑用户" : "添加用户";
		}
	},
	data() {
		return {
			logining: false,
			imgUrl: "",
			centerDialogVisible: false,
			newUser: {
				phoneNumber: "",
				userName: "",
				sex: "",
				age: "",
				password: "",
				userImage: "",
				imagesFile: ""
			},
			rules: {
				phoneNumber: [
					{
						required: true,
						message: "手机号不许为空",
						trigger: "blur"
					},
					{
						pattern: /^\d{11}$/,
						message: "手机号格式不正确",
						trigger: "blur"
					}
				],
				userName: [
					{
						required: true,
						message: "用户名不许为空",
						trigger: "blur"
					}
				],
				password: [
					{
						required: true,
						message: "密码不许为空",
						trigger: "blur"
					}
				],
				sex: [
					{
						required: true,
						message: "性别不许为空",
						trigger: "change"
					}
				],
				age: [
					{
						required: true,
						message: "年龄不许为空",
						trigger: "change"
					}
				]
			}
		};
	},
	methods: {
		...mapActions("user", ["addUser", "getUserDataById", "updateUser"]),
		initForm() {
			this.$refs.ruleForm.resetFields();
			this.imgUrl = "";
			this.newUser.imagesFile = "";
		},
		resetData() {
			this.initForm();
			if (this.disabled && this.centerDialogVisible) {
				this.getUserDataById(this.newUser.id).then(res => {
					this.newUser = res.data;
					this.imgUrl = this.newUser.userImage;
				});
			}
		},
		changeUpload(file) {
			this.imgUrl = URL.createObjectURL(file.raw);
			this.newUser.imagesFile = file.raw;
		},
		closeDialog() {
			this.initForm();
			this.newUser = {};
			this.$emit("clear");
		},
		submitData() {
			this.$refs.ruleForm.validate(valid => {
				if (valid) {
					let isImg = this.newUser.imagesFile
						? this.beforeAvatarUpload(this.newUser.imagesFile)
						: true;
					if (isImg) {
						this.logining = true;
						let formData = new FormData();
						Object.keys(this.newUser).forEach(key => {
							formData.append(key, this.newUser[key]);
						});
						if (this.newUser.imagesFile === "") {
							formData.delete("imagesFile");
						}
						formData.delete("createTime");
						if (this.disabled) {
							this.updateUserData(formData);
						} else {
							this.addUserData(formData);
						}
					}
				} else {
					this.$message.warning("用户信息不许为空");
				}
			});
		},
		beforeAvatarUpload(file) {
			let imgType = ["image/jpeg", "image/png", "image/gif", "image/jpg"];
			const isJPG = imgType.indexOf(file.type) !== -1;
			const isLt2M = file.size / 1024 / 1024 < 2;
			if (!isJPG) {
				this.$message.error("您上传的不是图片");
			} else if (!isLt2M) {
				this.$message.error("上传头像图片大小不能超过 2MB");
			}
			return isJPG && isLt2M;
		},
		addUserData(formData) {
			this.addUser(formData).then(res => {
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
		updateUserData(formData) {
			this.updateUser(formData).then(res => {
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
		userId: {
			immediate: true,
			handler(val) {
				if (this.disabled && val !== -1) {
					this.getUserDataById(val).then(res => {
						this.newUser = res.data;
						this.imgUrl = this.newUser.userImage;
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
.images-upload {
	text-align: right;
	margin-top: -120px;
	margin-right: 50px;
}

.avatar-uploader .el-upload .avatar-uploader-icon {
	border: 1px dashed #d9d9d9;
	border-radius: 6px;
	cursor: pointer;
	position: relative;
	overflow: hidden;
}

.avatar-uploader .el-upload .avatar-uploader-icon:hover {
	border-color: #409eff;
}

.avatar-uploader-icon {
	font-size: 28px;
	color: #8c939d;
	width: 178px;
	height: 178px;
	line-height: 178px;
	text-align: center;
}

.avatar {
	width: 178px;
	height: 178px;
	display: block;
	border-radius: 6px;
}
</style>
