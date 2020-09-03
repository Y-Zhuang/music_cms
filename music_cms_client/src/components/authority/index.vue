<template>
	<div class="authority-main">
		<main-header :isDisplay="false"></main-header>
		<el-card class="main-card">
			<el-table
				:data="authorityList"
				row-key="id"
				:tree-props="{
					children: 'children',
					hasChildren: 'hasChildren'
				}"
			>
				<el-table-column width="75">
					<template slot-scope="scope">
						<i :class="scope.row.icon"></i>
					</template>
				</el-table-column>
				<el-table-column
					prop="authorityName"
					label="权限名"
					width="200"
				>
				</el-table-column>
				<el-table-column
					prop="explanation"
					label="权限说明"
					width="300"
				>
				</el-table-column>
				<el-table-column prop="mark" label="权限标识" width="150">
				</el-table-column>
				<el-table-column prop="type" label="权限类型" width="150">
				</el-table-column>
				<el-table-column prop="icon" label="权限图标" width="300">
				</el-table-column>
				<el-table-column prop="api" label="操作API" width="280">
				</el-table-column>
				<el-table-column prop="createTime" label="创建事件" width="200">
				</el-table-column>
			</el-table>
		</el-card>
	</div>
</template>

<script>
/**
 * @ComponentName : authority
 * @Description   : 权限管理
 * @Author        : Zhuang
 * @Date          : 2020-04-27 20:36
 */

import { mapActions } from "vuex";
import { initMenu } from "@/utils/utils";
import mainHeader from "@/components/mainHeader";

export default {
	name: "authority",
	components: {
		mainHeader
	},
	data() {
		return {
			authorityList: [],
			authorityProps: {
				children: "children",
				label: "authorityName"
			}
		};
	},
	methods: {
		...mapActions("authority", ["getAuthorityAll"])
	},
	async created() {
		this.authorityList = initMenu((await this.getAuthorityAll()).data);
	}
};
</script>

<style scoped>
.authority-main {
	margin: 10px 0;
}

.main-card {
	margin: 5px 0;
}

.my-icon-yonghuguanli {
	font-size: 10px;
	margin-left: 2px;
}
</style>
