<template>
	<div class="song-main">
		<main-header
			:isDisplay="display('addSong')"
			v-on:centerDialog="openDialog"
			v-on:search="searchData"
		>
		</main-header>
		<el-card class="main-card">
			<el-table :data="songList" style="width: 100%" height="670px">
				<el-table-column prop="songName" label="歌曲名" width="360">
				</el-table-column>
				<el-table-column prop="singer" label="歌手名" width="300">
				</el-table-column>
				<el-table-column prop="album" label="专辑名" width="300">
				</el-table-column>
				<el-table-column label="歌曲" width="300">
					<template slot-scope="songList">
						<el-button
							v-if="songList.row.songUrl"
							type="primary"
							round
							size="mini"
							@click="playSong(songList.row.songUrl)"
						>
							播放音乐
						</el-button>
						<el-progress
							v-else-if="
								songList.row.id === songFileId && !uploadDisplay
							"
							:percentage="uploadPercent"
							:color="colors"
						></el-progress>
						<el-button
							@click="uploadShow(songList.row.id)"
							v-else
							type="success"
							round
							size="mini"
						>
							上传音乐
						</el-button>
					</template>
				</el-table-column>
				<el-table-column fixed="right" label="操作" width="200">
					<template slot-scope="scope">
						<el-popover
							placement="top"
							width="160"
							v-model="popoverShow"
							v-show="popoverId === scope.row.id"
						>
							<p>确定删除吗？</p>
							<div style="text-align: right; margin: 0">
								<el-button
									size="mini"
									type="text"
									@click="popoverId = ''"
								>
									取消
								</el-button>
								<el-button
									type="primary"
									size="mini"
									@click="deleteData(scope.row.id)"
								>
									确定
								</el-button>
							</div>
						</el-popover>
						<el-button
							v-if="display('deleteSong')"
							@click="popoverId = scope.row.id"
							type="text"
							size="small"
						>
							删除
						</el-button>
						<el-button
							v-if="display('editSong')"
							@click="editSong(scope.row.id)"
							type="text"
							size="small"
						>
							编辑
						</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<main-footer
			v-on:currentChange="currentChange"
			:totalPage="pageConf.totalPage"
			:pageSize="pageConf.pageSize"
		></main-footer>
		<song-detail
			:centerDialog="centerDialogVisible"
			:songId="songId"
			:disabled="disabled"
			v-on:clear="clearData"
			v-on:update="updateData"
		>
		</song-detail>
		<song-upload
			v-on:clear="clearData"
			v-on:upload="uploadSong"
			:isDisplay="uploadDisplay"
		></song-upload>
		<cms-audio
			:isDisplay="audioDisplay"
			v-on:clear="clearData"
			:url="url"
		></cms-audio>
	</div>
</template>

<script>
/**
 * @ComponentName : index
 * @Description   : 歌曲列表
 * @Author        : Zhuang
 * @Date          : 2020-04-27 20:39
 */

import { mapActions, mapGetters } from "vuex";
import { isDisplay, getMenuId } from "@/utils/utils";
import mainHeader from "@/components/mainHeader";
import mainFooter from "@/components/mainFooter";
import cmsAudio from "@/components/cmsAudio";
import songDetail from "./module/songDetail";
import songUpload from "./module/songUpload";

export default {
	name: "song",
	components: {
		mainHeader,
		mainFooter,
		songDetail,
		songUpload,
		cmsAudio
	},
	data() {
		return {
			url: "",
			search: "",
			isUpload: false,
			audioDisplay: false,
			uploadDisplay: false,
			popoverId: "",
			popoverShow: true,
			disabled: false,
			songList: [],
			userView: [],
			centerDialogVisible: false,
			songId: -1,
			songFileId: -1,
			uploadPercent: 0,
			countTime: "",
			pageConf: {
				//当前页
				pageCode: 1,
				//一页显示条数
				pageSize: 11,
				//总记录数
				totalPage: 12
			},
			colors: [
				{ color: "#f56c6c", percentage: 20 },
				{ color: "#e6a23c", percentage: 40 },
				{ color: "#5cb87a", percentage: 60 },
				{ color: "#1989fa", percentage: 80 },
				{ color: "#6f7ad3", percentage: 100 }
			]
		};
	},
	methods: {
		...mapGetters("song", ["getSongList", "getTotal"]),
		...mapGetters("authority", ["getMenuList"]),
		...mapGetters("user", ["getUserId"]),
		...mapActions("authority", ["getUserViewByUserId"]),
		...mapActions("song", [
			"getSongData",
			"uploadData",
			"getUploadPercent",
			"resetPercent",
			"updateSongUrl",
			"deleteSong"
		]),
		deleteData(id) {
			this.deleteSong(id).then(res => {
				if (res.isok) {
					this.updateData();
					this.$message.success(res.data);
				} else {
					this.$message.error(res.message);
				}
				this.popoverId = "";
			});
		},
		editSong(id) {
			this.songId = id;
			this.disabled = true;
			this.centerDialogVisible = true;
		},
		getData(data) {
			this.getSongData(data).then(() => {
				this.songList = this.getSongList();
				this.pageConf.totalPage = this.getTotal();
			});
		},
		clearData() {
			this.url = "";
			this.songId = -1;
			this.centerDialogVisible = false;
			this.uploadDisplay = false;
			this.audioDisplay = false;
			if (!this.isUpload) {
				this.songFileId = -1;
			}
		},
		openDialog() {
			this.disabled = false;
			this.centerDialogVisible = true;
		},
		currentChange(pageCode, pageSize) {
			this.pageConf.pageCode = pageCode;
			this.pageConf.pageSize = pageSize;
			this.getData({
				pageCode: pageCode,
				pageSize: pageSize,
				search: this.search
			});
		},
		updateData() {
			this.getData({
				pageCode: this.pageConf.pageCode,
				pageSize: this.pageConf.pageSize,
				search: this.search
			});
		},
		display: function(mark) {
			return isDisplay(this.userView, mark);
		},
		uploadShow(id) {
			if (!this.isUpload) {
				this.uploadPercent = 0;
				this.songFileId = id;
				this.uploadDisplay = true;
			}
		},
		async uploadSong(file) {
			if (!this.isUpload) {
				try {
					this.isUpload = true;
					this.uploadDisplay = false;
					this.uploadPercent = 0;
					let formData = new FormData();
					formData.append("id", this.songFileId);
					formData.append("songFile", file);
					this.countTime = setInterval(this.showStatus,200);
					let res = await this.uploadData(formData);
					if (res.isok) {
						this.$message.success(res.data);
						this.updateData();
					} else {
						this.$message.error("上传失败");
					}
				} catch (e) {
					this.$message.error("上传失败");
				}
				finally {
					clearInterval(this.countTime);
					this.countTime = null;
					this.isUpload = false;
					this.songFileId = -1;
				}
			}
		},
		async showStatus() {
			try {
				let percent = (await this.getUploadPercent()).data;
				if (percent >= 100) {
					percent = 100;
					clearInterval(this.countTime);
					this.countTime = null;
					this.resetPercent();
				}
				this.uploadPercent = percent;
			} catch (e) {
				console.log(e);
			}
		},
		playSong(url) {
			this.url = url;
			this.audioDisplay = true;
		},
		searchData(val) {
			this.search = val;
			this.updateData();
		}
	},
	async created() {
		this.getData({
			pageCode: this.pageConf.pageCode,
			pageSize: this.pageConf.pageSize,
			search: this.search
		});
		let menuId = getMenuId(
			this.getMenuList(),
			this.$router.history.current.name
		);
		if (menuId !== null && menuId !== "") {
			this.userView = (
				await this.getUserViewByUserId({
					userId: this.getUserId(),
					superiorId: menuId
				})
			).data;
		}
	}
};
</script>

<style scoped>
.song-main {
	margin: 10px 0;
}

.main-card {
	margin: 5px 0;
}
</style>
