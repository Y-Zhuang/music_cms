<template>
	<el-card :body-style="{ padding: '0px', height: '48px' }">
		<el-tag
			v-for="(tag, index) in tags"
			:key="tag.name"
			size="medium"
			type="info"
			closable
			@click="clickTag(index)"
			@close="closeTag(index)"
			:disable-transitions="true"
		>
			{{ tag.name }}
		</el-tag>
	</el-card>
</template>

<script>
/**
 * @ComponentName : index
 * @Description   : tag组件
 * @Author        : Zhuang
 * @Date          : 2020-04-23 16:50
 */

export default {
	name: "m-tag",
	props: {
		tags: {
			type: Array,
			defaults: () => []
		}
	},
	methods: {
		clickTag(index) {
			this.$emit("clickTag", index);
		},
		closeTag(index) {
			this.$emit("closeTag", index);
		}
	}
};
</script>

<style scoped>
.el-tag.el-tag--info {
	border: 0;
	background-color: #6c4c49;
	margin-right: 10px;
	color: #7c8577;
	margin-top: 10px;
	margin-left: 10px;
	cursor: pointer;
}
</style>
