<template>
	<fragment>
		<template v-for="item in this.menuData">
			<el-submenu
				v-if="item.children.length > 0"
				:key="item.id"
				:index="item.authorityName"
			>
				<template slot="title">
					<i :class="item.icon + ' my-icon'"></i>
					<span>{{ item.authorityName }}</span>
				</template>
				<submenu
					v-on:itemOpen="itemOpen"
					:menuData="item.children"
				></submenu>
			</el-submenu>
			<el-menu-item
				@click="
					itemOpen({
						name: item.authorityName,
						mark: item.mark,
						superiorId: item.superiorId
					})
				"
				v-else
				:index="item.mark"
				:key="item.id"
			>
				<i :class="item.icon + ' my-icon'"></i>
				<span>{{ item.authorityName }}</span>
			</el-menu-item>
		</template>
	</fragment>
</template>

<script>
/**
 * @ComponentName : submenu
 * @Description   : 侧边菜单递归组件
 * @Author        : Zhuang
 * @Date          : 2020-04-29 21:00
 */

export default {
	name: "submenu",
	props: {
		menuData: {
			type: Array,
			defaults: () => []
		}
	},
	methods: {
		itemOpen(data) {
			this.$emit("itemOpen", data);
		}
	}
};
</script>

<style scoped>
.my-icon-yonghuguanli {
	font-size: 10px;
	margin-left: 2px;
}

.my-icon {
	margin-right: 10px;
}
</style>
