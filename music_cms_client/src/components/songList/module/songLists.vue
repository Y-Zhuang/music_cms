<template>
	<el-dialog
		title="歌单详情"
		:visible="centerDialogVisible"
		@close="closeDialog"
		width="60%"
		center
	>
		<el-table :data="songList" height="500" border style="width: 100%">
			<el-table-column prop="songName" label="歌曲名" width="200">
			</el-table-column>
			<el-table-column prop="singer" label="歌手名" width="300">
			</el-table-column>
			<el-table-column prop="album" label="专辑名" width="300">
			</el-table-column>
			<el-table-column fixed="right" label="操作" width="200">
				<template slot-scope="scope">
					<el-button
						v-if="scope.row.songUrl"
						@click="playSong(scope.row.songUrl)"
						type="text"
						size="mini"
					>
						试听
					</el-button>
					<span v-else>无法试听</span>
				</template>
			</el-table-column>
		</el-table>
		<cms-audio
			:isDisplay="audioDisplay"
			v-on:clear="clearData"
			:url="url"
		></cms-audio>
	</el-dialog>
</template>

<script>
import { mapActions } from "vuex";
import cmsAudio from "@/components/cmsAudio";

/**
 * @ComponentName : songLists
 * @Description   : 歌曲列表组件
 * @Author        : Zhuang
 * @Date          : 2020-05-09 9:42
 */

export default {
	name: "songLists",
	components: {
		cmsAudio
	},
	props: {
		centerDialog: {
			type: Boolean,
			required: true,
			defaults: false
		},
		songListId: {
			type: Number,
			required: true,
			defaults: -1
		}
	},
	data() {
		return {
			url: "",
			songList: [],
			audioDisplay: false,
			centerDialogVisible: false
		};
	},
	methods: {
		...mapActions("songList", ["getSongBySongListId"]),
		closeDialog() {
			this.$emit("clear");
		},
		playSong(url) {
			this.url = url;
			this.audioDisplay = true;
		},
		clearData() {
			this.url = "";
			this.audioDisplay = false;
		}
	},
	watch: {
		songListId: {
			immediate: true,
			handler(val) {
				if (val !== -1) {
					this.getSongBySongListId(val).then(res => {
						this.songList = res.data;
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
