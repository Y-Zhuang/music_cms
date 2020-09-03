<template>
	<el-dialog
		:title="getTitle"
		:visible="centerDialogVisible"
		@close="closeDialog"
		width="40%"
		center
	>
		<el-form
			:model="newSong"
			:rules="rules"
			ref="ruleForm"
			label-width="100px"
			class="demo-ruleForm"
		>
			<el-form-item label="歌曲名" prop="songName">
				<el-input
					placeholder="请输入歌曲名"
					v-model.trim="newSong.songName"
				></el-input>
			</el-form-item>
			<el-form-item label="歌手名" prop="singer">
				<el-input
					placeholder="请输入歌手名"
					v-model.trim="newSong.singer"
				></el-input>
			</el-form-item>
			<el-form-item label="专辑名" prop="album">
				<el-input
					placeholder="请输入专辑名"
					v-model.trim="newSong.album"
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
 * @ComponentName : songDetail
 * @Description   : 歌曲详情组件
 * @Author        : Zhuang
 * @Date          : 2020-05-07 5:07
 */

import { mapActions } from "vuex";

export default {
	name: "songDetail",
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
		songId: {
			type: Number,
			required: true,
			defaults: -1
		}
	},
	computed: {
		getTitle: function() {
			return this.disabled ? "编辑歌曲" : "添加歌曲";
		}
	},
	data() {
		return {
			logining: false,
			centerDialogVisible: false,
			newSong: {
				songName: "",
				singer: "",
				album: ""
			},
			rules: {
				songName: [
					{
						required: true,
						message: "歌曲名不许为空",
						trigger: "blur"
					}
				],
				singer: [
					{
						required: true,
						message: "歌手名不许为空",
						trigger: "blur"
					}
				],
				album: [
					{
						required: true,
						message: "专辑名不许为空",
						trigger: "blur"
					}
				]
			}
		};
	},
	methods: {
		...mapActions("song", ["addSong", "updateSong", "getSongById"]),
		closeDialog() {
			this.$refs.ruleForm.resetFields();
			this.$emit("clear");
		},
		resetForm() {
			this.$refs.ruleForm.resetFields();
			if (this.disabled && this.centerDialogVisible) {
				this.getSongById(this.newSong.id).then(res => {
					this.newSong = res.data;
				});
			}
		},
		submitData() {
			this.$refs.ruleForm.validate(valid => {
				if (valid) {
					this.logining = true;
					let formData = new FormData();
					Object.keys(this.newSong).forEach(key => {
						formData.append(key, this.newSong[key]);
					});
					if (this.disabled) {
						this.updateSongData(formData);
					} else {
						this.addSongData(formData);
					}
				}
			});
		},
		addSongData(formData) {
			this.addSong(formData).then(res => {
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
		updateSongData(formData) {
			this.updateSong(formData).then(res => {
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
		songId: {
			immediate: true,
			handler(val) {
				if (this.disabled && val !== -1) {
					this.getSongById(val).then(res => {
						this.newSong = res.data;
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
