<template>
	<div class="Login">
		<user-login></user-login>
	</div>
</template>

<script>
import userLogin from "@/components/userLogin/login.vue";

export default {
	name: "Login",
	components: {
		userLogin
	}
};
</script>
