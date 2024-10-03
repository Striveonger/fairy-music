<template>
    <div class="music">
        <h1>Fairy Music</h1>
        <div class="search">
            <input v-model="searchQuery" @keydown.enter="searchMusic" placeholder="搜索音乐..." />
            <button @click="searchMusic" :disabled="isLoading">搜索</button>
        </div>
        <div v-if="isLoading" class="loading">
            正在搜索...
        </div>
        <div v-else-if="playlist.length > 0" class="playlist">
            <h2>播放列表</h2>
            <ul>
                <li v-for="(song, index) in playlist" :key="index" @click="playSong(song)">
                    <img :src="coverProxy(song)" :title="song.title" />
                    <span>{{ song.title }}</span>
                </li>
            </ul>
        </div>
        <div v-else-if="searchPerformed" class="no-results">
            没有找到相关音乐
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { search } from '../apis/Music'
import { SearchItem } from '../types';

const searchQuery = ref('');
let playlist = ref<Array<SearchItem>>([]);
const searchPerformed = ref(false);
const isLoading = ref(false);
const page = ref(1);
const searchMusic = async () => {
    searchPerformed.value = true;
    isLoading.value = true;
    try {
        const result = await search(searchQuery.value, page.value);
        // console.log("data: {}", result);
        playlist.value = result;
    } catch (error) {
        console.error('error fetching music:', error);
        playlist.value = [];
    } finally {
        isLoading.value = false;
    }
};

const playSong = (song: SearchItem) => {
    console.log(`正在播放: ${song.url}`);
    // 跳转到播放页面，并传递歌曲信息
    
};

const coverProxy = (song: SearchItem) => {
    // console.log('song :>> ', song);
    return "/api/v1/fairy/music/cover?url=" + song.cover;
}

</script>

<style scoped>
.playlist ul {
    list-style-type: none;
    padding: 0;
    margin-left: 10px;
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
}

.playlist li {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 10px;
    cursor: pointer;
    width: 400px;
}

.playlist li img {
    width: 400px;
    height: 230px;
    margin-bottom: 10px;
    object-fit: cover;
}

.playlist li span {
    text-align: center;
    max-width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>