<template>
	<el-dialog
		:title="getTitle"
		:visible="centerDialogVisible"
		@close="closeDialog"
		width="50%"
		center
	>
		<el-form
			:model="newSongList"
			:rules="rules"
			ref="ruleForm"
			label-width="100px"
			class="demo-ruleForm"
		>
			<el-form-item label="歌单名" prop="songListName">
				<el-input
					placeholder="请输入歌单名"
					v-model.trim="newSongList.songListName"
				></el-input>
			</el-form-item>
			<el-form-item label="简介" prop="introduction">
				<el-input
					placeholder="请输入简介"
					v-model.trim="newSongList.introduction"
				></el-input>
			</el-form-item>
		</el-form>
		<el-transfer
			ref="transfer"
			class="box-transfer"
			filterable
			:titles="['未加入歌单', '已加入歌单']"
			filter-placeholder="请输入歌曲名"
			v-model="transferValue"
			:data="songList"
		>
		</el-transfer>
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
 * @ComponentName : songListDetail
 * @Description   : 歌单详情组件
 * @Author        : Zhuang
 * @Date          : 2020-05-09 1:15
 */

import { mapActions } from "vuex";

export default {
	name: "songListDetail",
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
		songListId: {
			type: Number,
			required: true,
			defaults: -1
		},
		songList: {
			type: Array,
			defaults: () => []
		}
	},
	computed: {
		getTitle: function() {
			return this.disabled ? "编辑歌单" : "添加歌单";
		}
	},
	data() {
		return {
			transferValue: [],
			logining: false,
			centerDialogVisible: false,
			newSongList: {
				songListName: "",
				introduction: ""
			},
			rules: {
				songListName: [
					{
						required: true,
						message: "歌单名不许为空",
						trigger: "blur"
					}
				],
				introduction: [
					{
						required: true,
						message: "简介不许为空",
						trigger: "blur"
					}
				]
			}
		};
	},
	methods: {
		...mapActions("songList", [
			"addSongList",
			"updateSongList",
			"getSongListById",
			"getSongId"
		]),
		closeDialog() {
			this.initForm();
			this.$emit("clear");
		},
		initForm() {
			this.$refs.ruleForm.resetFields();
			this.$refs.transfer.clearQuery("left");
			this.$refs.transfer.clearQuery("right");
			this.transferValue = [];
		},
		resetData() {
			this.initForm();
			if (this.disabled && this.centerDialogVisible) {
				this.getSongListById(this.newSongList.id).then(res => {
					this.newSongList = res.data;
				});
				this.getSongId(this.newSongList.id).then(res => {
					this.transferValue = res.data;
				});
			}
		},
		submitData() {
			this.$refs.ruleForm.validate(valid => {
				if (valid) {
					this.logining = true;
					let formData = new FormData();
					Object.keys(this.newSongList).forEach(key => {
						formData.append(key, this.newSongList[key]);
					});
					formData.delete("createTime");
					formData.append("songId", this.transferValue);
					if (this.disabled) {
						this.updateSongListData(formData);
					} else {
						this.addSongListData(formData);
					}
				}
			});
		},
		addSongListData(formData) {
			this.addSongList(formData).then(res => {
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
		updateSongListData(formData) {
			if (this.transferValue.length) {
				this.updateSongList(formData).then(res => {
					if (res.isok) {
						this.$emit("update");
						this.$message.success(res.data);
						this.centerDialogVisible = false;
					} else {
						this.$message.error(res.message);
					}
				});
			} else {
				this.$message.warning("请先分配歌曲");
			}
			this.logining = false;
		}
	},
	watch: {
		songListId: {
			immediate: true,
			handler(val) {
				if (this.disabled && val !== -1) {
					this.getSongListById(val).then(res => {
						this.newSongList = res.data;
					});
					this.getSongId(val).then(res => {
						this.transferValue = res.data;
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
.box-transfer {
	margin: 0 auto;
	width: 70%;
}
</style>
