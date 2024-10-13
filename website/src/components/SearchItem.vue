<template>
    <li @click="playSong(song)" class="col-2">
        <img :src="coverProxy(song)" :title="song.title" />
        <span>{{ song.title }}</span>
    </li>
</template>

<script setup lang="ts">
import router from '@/router'
import { SearchItem } from '@/types';

const props = defineProps<{song: SearchItem }>();

const coverProxy = (song: SearchItem) => {
    // console.log('song :>> ', song);
    return "/api/v1/fairy/music/cover?url=" + song.cover;
}

const playSong = (song: SearchItem) => {
    console.log(`正在跳转到播放页面，歌曲信息:`, song);
    console.log(`歌曲标题: ${song.title}`);
    console.log(`歌曲URL: ${song.url}`);
    console.log(`歌曲封面: ${song.cover}`);
    
    // 跳转到播放页面，并传递歌曲信息
    router.push({name: 'play', params: {url: song.url}});
};
</script>

<style scoped lang="scss">
li {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 10px;
    cursor: pointer;
    width: 400px;
    img {
        max-width: 400px;
        max-height: 230px;
        min-width: 200px;
        min-height: 115px;
        margin-bottom: 10px;
        object-fit: cover;
    }
    span {
        text-align: center;
        max-width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
}
</style>
