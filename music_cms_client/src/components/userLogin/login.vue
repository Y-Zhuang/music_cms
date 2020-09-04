<template>
	<div class="login-container">
		<el-form
			:model="ruleForm"
			:rules="rules"
			status-icon
			ref="ruleForm"
			label-position="left"
			label-width="0px"
			class="demo-ruleForm login-page"
		>
			<h3 class="title">系统登录</h3>
			<el-form-item prop="phoneNumber">
				<el-input
					type="text"
					v-model.trim="ruleForm.phoneNumber"
					auto-complete="off"
					placeholder="手机号"
				></el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input
					type="password"
					v-model.trim="ruleForm.password"
					auto-complete="off"
					placeholder="密码"
				></el-input>
			</el-form-item>
			<el-form-item style="width:100%;">
				<el-button
					type="primary"
					style="width:100%;"
					@click="handleSubmit()"
					:loading="logining"
				>登录
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
/**
 * @ComponentName : login
 * @Description   : 登录组件
 * @Author        : Zhuang
 * @Date          : 2020-04-21 17:51
 */
import { mapActions } from "vuex";
import { setToKen } from "@/utils/utils";

export default {
	name: "login",
	data() {
		return {
			logining: false,
			ruleForm: {
				phoneNumber: "12345678910",
				password: "admin"
			},
			rules: {
				phoneNumber: [
					{
						required: true,
						message: "请输入手机号",
						trigger: "blur"
					},
					{
						pattern: /^\d{11}$/,
						message: "手机号格式不正确",
						trigger: "blur"
					}
				],
				password: [
					{
						required: true,
						message: "请输入密码",
						trigger: "blur"
					}
				]
			}
		};
	},
	methods: {
		...mapActions("user", ["userLogin"]),
		handleSubmit() {
			this.$refs.ruleForm.validate(async valid => {
				if (valid) {
					this.logining = true;
					let formData = new FormData();
					Object.keys(this.ruleForm).forEach(key => {
						formData.append(key, this.ruleForm[key]);
					});
					try {
						let res = await this.userLogin(formData);
						if (res.isok) {
							setToKen(res.data);
							this.$router.push({ name: "Home" });
							this.$message.success("登录成功");
						} else {
							this.$message.warning(res.message);
						}
					} catch (e) {
						this.$message.warning("登录失败");
					}
					this.logining = false;
				}
			});
		}
	}
};
</script>

<style scoped>
.login-container {
	width: 100%;
	height: 100%;
}

.login-page {
	-webkit-border-radius: 5px;
	border-radius: 5px;
	margin: 180px auto;
	width: 350px;
	padding: 35px 35px 35px;
	background: #fff;
	border: 1px solid #eaeaea;
	box-shadow: 0 0 25px #cac6c6;
}

.label.el-checkbox.rememberme {
	margin: 0 0 15px;
	text-align: left;
}

.title {
	color: #46485f;
	text-align: center;
	margin-bottom: 30px;
}
</style>
