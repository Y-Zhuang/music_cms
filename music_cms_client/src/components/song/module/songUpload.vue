<template>
	<el-dialog
		title="上传歌曲"
		:visible="dialogVisible"
		@close="closeDialog"
		width="30%"
	>
		<el-button
			class="up-btn"
			type="primary"
			icon="el-icon-circle-plus-outline"
			@click="openInput()"
			circle
		></el-button>
		<input type="file" ref="upFile" class="up-file" @change="getFile()" />
		<div class="file-name" v-if="fileName">已选择：{{ fileName }}</div>
		<span slot="footer" class="dialog-footer">
			<el-button @click="closeDialog()">取 消</el-button>
			<el-button type="primary" @click="submitData()">确 定</el-button>
		</span>
	</el-dialog>
</template>

<script>
/**
 * @ComponentName : songUpload
 * @Description   : 上传MP3组件
 * @Author        : Zhuang
 * @Date          : 2020-05-08 2:32
 */

export default {
	name: "songUpload",
	props: {
		isDisplay: {
			type: Boolean,
			required: true,
			defaults: false
		}
	},
	data() {
		return {
			dialogVisible: false,
			fileName: "",
			songFile: ""
		};
	},
	methods: {
		submitData() {
			if (this.beforeAvatarUpload(this.songFile)) {
				this.$emit("upload", this.songFile);
			}
		},
		closeDialog() {
			this.songFile = "";
			this.fileName = "";
			this.$refs.upFile.value = null;
			this.$emit("clear");
		},
		openInput() {
			this.$refs.upFile.dispatchEvent(new MouseEvent("click"));
		},
		getFile() {
			this.songFile = this.$refs.upFile.files[0];
			this.fileName = this.songFile.name;
		},
		beforeAvatarUpload(file) {
			const isJPG = file.type === "audio/mpeg";
			const isMax = file.size / 1024 / 1024 < 500;
			if (file === "") {
				this.$message.error("您选择的文件为空");
			}
			else if (!isJPG) {
				this.$message.error("您上传的不是音频");
			}
			else if (!isMax) {
				this.$message.error("上传音频大小不能超过 500MB");
			}
			return isJPG && isMax && file !== "";
		}
	},
	watch: {
		isDisplay: {
			immediate: true,
			handler(val) {
				this.dialogVisible = val;
			}
		}
	}
};
</script>

<style scoped>
.up-btn {
	display: block;
	margin: 10px auto;
}

.up-file {
	display: none;
}

.file-name {
	width: 100%;
	text-align: center;
}
</style>
