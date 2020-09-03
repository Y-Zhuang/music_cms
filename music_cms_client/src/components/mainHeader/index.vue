<template>
	<el-card class="header-card">
		<el-button
			v-if="isDisplay"
			type="success"
			size="mini"
			icon="el-icon-plus"
			@click="centerDialog()"
		></el-button>
		<el-input
			placeholder="请输入内容"
			v-model="searchText"
			size="mini"
			class="input-search"
		>
			<el-button
				@click="search"
				slot="append"
				icon="el-icon-search"
			></el-button>
		</el-input>
	</el-card>
</template>

<script>
/**
 * @ComponentName : index
 * @Description   : 内容页头部
 * @Author        : Zhuang
 * @Date          : 2020-04-23 17:52
 */

export default {
	name: "main-header",
	props: {
		isDisplay: {
			type: Boolean,
			required: true,
			defaults: false
		}
	},
	data() {
		return {
			searchText: ""
		};
	},
	methods: {
		search() {
			this.$emit("search", this.searchText);
		},
		centerDialog() {
			this.$emit("centerDialog");
		}
	}
};
</script>

<style scoped>
.header-card {
	height: 60px;
}

.input-search {
	float: right;
	width: 30%;
	margin: auto 0;
}
</style>
