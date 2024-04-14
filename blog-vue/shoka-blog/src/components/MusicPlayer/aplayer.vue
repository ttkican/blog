<template>
	<div ref="playerRef"></div>
</template>

<script setup lang="ts">
import request from "@/utils/request";
import type { PropType } from '@vue/runtime-core';
import APlayer from 'APlayer';
import 'APlayer/dist/APlayer.min.css';
import { AxiosPromise } from "axios";

const playerRef = ref()
let instance: any;

// APlayer歌曲信息
class Audio {
	// 音频艺术家
	author: String;
	// 音频名称
	title: String;
	// 音频链接
	url: String;
	// 音频封面
	pic: String;
	// 歌词
	lrc: String;

	constructor(artist: String, name: String, url: String, cover: String, lrc: String) {
		this.author = artist;
		this.title = name;
		this.url = url;
		this.pic = cover;
		this.lrc = lrc;
	}
}

const props = defineProps({
	// 开启吸底模式
	fixed: {
		type: Boolean as PropType<boolean>,
		default: true
	},
	// 开启迷你模式
	mini: {
		type: Boolean as PropType<boolean>,
		default: true
	},
	// 音频自动播放
	autoplay: {
		type: Boolean as PropType<boolean>,
		default: false
	},
	// 主题色
	theme: {
		type: String as PropType<string>,
		default: 'rgba(255,255,255,0.2)'
	},
	// 音频循环播放
	loop: {
		type: String as PropType<'all' | 'one' | 'none'>,
		default: 'all'
	},
	// 音频循环顺序
	order: {
		type: String as PropType<'list' | 'random'>,
		default: 'random'
	},
	// 预加载
	preload: {
		type: String as PropType<'auto' | 'metadata' | 'none'>,
		default: 'auto'
	},
	// 默认音量
	volume: {
		type: Number as PropType<number>,
		default: 0.7,
	},
	// 歌曲服务器(netease-网易云, tencent-qq音乐, kugou-酷狗, xiami-小米音乐, baidu-百度音乐)
	server: {
		type: String as PropType<'netease' | 'tencent' | 'kugou' | 'xiami' | 'baidu'>,
		default: 'netease'
	},
	// 播放类型(song-歌曲, playlist-播放列表, album-专辑, search-搜索, artist-艺术家)
	type: {
		type: String as PropType<string>,
		default: 'playlist'
	},
	// 歌的id
	id: {
		type: String as PropType<string>,
		default: '19723756'
	},
	// 互斥，阻止多个播放器同时播放，当前播放器播放时暂停其他播放器
	mutex: {
		type: Boolean as PropType<boolean>,
		default: true
	},
	// 传递歌词方式
	lrcType: {
		type: Number as PropType<number>,
		default: 3
	},
	// 列表是否默认折叠
	listFolded: {
		type: Boolean as PropType<boolean>,
		default: true
	},
	// 列表最大高度
	listMaxHeight: {
		type: String as PropType<string>,
		default: '100px'
	},
	// 存储播放器设置的 localStorage key
	storageName: {
		type: String as PropType<string>,
		default: 'aplayer-setting'
	}
})

// 初始化
onMounted(() => {
	nextTick(() => {
		request<AxiosPromise<Audio[]>>({
			url: `https://api.i-meto.com/meting/api?server=${props.server}&type=${props.type}&id=${props.id}&r=${Math.random()}`,
			method: "get",
		}).then(({data}) => {
			instance = new APlayer({
				container: playerRef.value,
				fixed: props.fixed,
				mini: props.mini,
				autoplay: props.autoplay,
				theme: props.theme,
				loop: props.loop,
				order: props.order,
				preload: props.preload,
				volume: props.volume,
				mutex: props.mutex,
				lrcType: props.lrcType,
				listFolded: props.listFolded,
				listMaxHeight: props.listMaxHeight,
				storageName: props.storageName,
				audio: data
			})
		});
	})
})
// 销毁
onBeforeUnmount(() => {
	instance.destroy()
})
</script>

<style scoped></style>
