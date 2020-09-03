<template>
	<el-dialog
		title="播放歌曲"
		:visible="dialogVisible"
		@close="closeDialog"
		:append-to-body="true"
		:close-on-click-modal="false"
		width="20%"
	>
		<vue-audio-native
			:url="url"
			:showCurrentTime="true"
			:showVolume="true"
			:showDownload="true"
			:autoplay="true"
			size="small"
			@on-audioId="onAudioId"
		></vue-audio-native>
	</el-dialog>
</template>

<script>
/**
 * @ComponentName : cms-audio
 * @Description   : 播放音乐组件
 * @Author        : Zhuang
 * @Date          : 2020-05-08 9:58
 */

export default {
	name: "cms-audio",
	props: {
		isDisplay: {
			type: Boolean,
			required: true,
			defaults: false
		},
		url: {
			type: String,
			required: true,
			defaults: ""
		}
	},
	data() {
		return {
			dialogVisible: false,
			audioId: ""
		};
	},
	methods: {
		closeDialog() {
			document.getElementById(this.audioId).pause();
			this.$emit("clear");
		},
		onAudioId(event) {
			this.audioId = event;
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

<style scoped></style>
