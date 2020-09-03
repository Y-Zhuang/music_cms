<template>
	<el-card class="header-card" :body-style="{ padding: '0px' }">
		<el-pagination
			class="footer-pagination"
			:small="true"
			background
			layout="prev, pager, next"
			:total="totalPage"
			@current-change="handleCurrentChange"
			:current-page="pageCode"
			:page-size="pageSize"
		>
		</el-pagination>
	</el-card>
</template>

<script>
/**
 * @ComponentName : index
 * @Description   : 内容页底部
 * @Author        : Zhuang
 * @Date          : 2020-04-23 17:52
 */

export default {
	name: "main-header",
	props: {
		totalPage: {
			type: Number,
			required: true
		},
		pageSize: {
			type: Number,
			required: true
		}
	},
	data() {
		return {
			pageCode: 1
		};
	},
	methods: {
		handleCurrentChange(val) {
			this.$emit("currentChange", val, this.pageSize);
		}
	}
};
</script>

<style scoped>
.header-card {
	height: 50px;
}

.footer-pagination {
	float: right;
	margin: 10px;
}
</style>
