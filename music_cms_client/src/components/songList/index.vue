<template>
	<div class="song-list-main">
		<main-header
			:isDisplay="display('addSongList')"
			v-on:centerDialog="openDialog"
			v-on:search="searchData"
		></main-header>
		<el-card class="main-card">
			<el-table :data="songLists" style="width: 100%" height="670px">
				<el-table-column prop="songListName" label="歌单名" width="450">
				</el-table-column>
				<el-table-column prop="introduction" label="简介" width="450">
				</el-table-column>
				<el-table-column prop="createTime" label="创建时间" width="400">
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
							v-if="display('deleteSongList')"
							@click="popoverId = scope.row.id"
							type="text"
							size="small"
						>
							删除
						</el-button>
						<el-button
							v-if="display('editSongList')"
							@click="editSongList(scope.row.id)"
							type="text"
							size="small"
						>
							编辑
						</el-button>
						<el-button
							@click="seeDetails(scope.row.id)"
							type="text"
							size="small"
						>
							查看详情
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
		<song-list-detail
			:centerDialog="centerDialogVisible"
			:disabled="disabled"
			:songListId="songListId"
			:songList="songList"
			v-on:clear="clearData"
			v-on:update="updateData"
		>
		</song-list-detail>
		<song-lists
			:centerDialog="songListVisible"
			:songListId="seeSongListId"
			v-on:clear="clearData"
		></song-lists>
	</div>
</template>

<script>
/**
 * @ComponentName : index
 * @Description   : 歌单列表
 * @Author        : Zhuang
 * @Date          : 2020-04-27 20:40
 */

import { mapGetters, mapActions } from "vuex";
import { getMenuId, isDisplay, initSong } from "@/utils/utils";
import mainHeader from "@/components/mainHeader";
import mainFooter from "@/components/mainFooter";
import songListDetail from "./module/songListDetail";
import songLists from "./module/songLists";

export default {
	name: "index",
	components: {
		mainHeader,
		mainFooter,
		songListDetail,
		songLists
	},
	data() {
		return {
			search: "",
			popoverId: "",
			popoverShow: true,
			disabled: false,
			songList: [],
			songLists: [],
			userView: [],
			centerDialogVisible: false,
			songListVisible: false,
			songListId: -1,
			seeSongListId: -1,
			pageConf: {
				//当前页
				pageCode: 1,
				//一页显示条数
				pageSize: 11,
				//总记录数
				totalPage: 12
			}
		};
	},
	methods: {
		...mapGetters("authority", ["getMenuList"]),
		...mapGetters("songList", ["getSongLists", "getTotal"]),
		...mapGetters("user", ["getUserId"]),
		...mapActions("song", ["getSong"]),
		...mapActions("authority", ["getUserViewByUserId"]),
		...mapActions("songList", ["getSongListData", "deleteSongList"]),
		getData(data) {
			this.getSongListData(data).then(() => {
				this.songLists = this.getSongLists();
				this.pageConf.totalPage = this.getTotal();
			});
		},
		display: function(mark) {
			return isDisplay(this.userView, mark);
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
		clearData() {
			this.songListId = -1;
			this.seeSongListId = -1;
			this.centerDialogVisible = false;
			this.songListVisible = false;
		},
		updateData() {
			this.getData({
				pageCode: this.pageConf.pageCode,
				pageSize: this.pageConf.pageSize,
				search: this.search
			});
		},
		openDialog() {
			this.disabled = false;
			this.centerDialogVisible = true;
		},
		deleteData(id) {
			this.deleteSongList(id).then(res => {
				if (res.isok) {
					this.updateData();
					this.$message.success(res.data);
				} else {
					this.$message.error(res.message);
				}
				this.popoverId = "";
			});
		},
		editSongList(id) {
			this.songListId = id;
			this.disabled = true;
			this.centerDialogVisible = true;
		},
		seeDetails(id) {
			this.seeSongListId = id;
			this.songListVisible = true;
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
		this.songList = initSong((await this.getSong()).data);
	}
};
</script>

<style scoped>
.song-list-main {
	margin: 10px 0;
}

.main-card {
	margin: 5px 0;
}
</style>
