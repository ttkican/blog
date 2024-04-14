<template>
	<Provider>
		<div class="app-wrapper">
			<Header></Header>
			<main class="main-wrapper">
				<router-view v-slot="{ Component, route }">
					<keep-alive>
						<component :is="Component" :key="route.path" />
					</keep-alive>
				</router-view>
			</main>
			<Footer></Footer>
			<Tool></Tool>
			<Search></Search>
			<Login></Login>
			<Register></Register>
			<Forget></Forget>
			<Email></Email>
			<Drawer></Drawer>
			<MusicPlayer></MusicPlayer>
			<ChatRoom></ChatRoom>
		</div>
	</Provider>
</template>

<script setup lang="ts">
onMounted(() => {
	console.log(
		"%c Hello World %c By 阿冬 %c",
		"background:#e9546b ; padding: 1px; border-radius: 3px 0 0 3px;  color: #fff; padding:5px 0;",
		"background:#ec8c69 ; padding: 1px; border-radius: 0 3px 3px 0;  color: #000; padding:5px 0;",
		"background:transparent"
	);
})
</script>

<style scoped>
.app-wrapper {
	position: relative;
	min-height: 100vh;

}

.main-wrapper {
	display: flex;
	flex-direction: column;
	width: 100%;
	padding: 0 0 8rem;
}
</style>
