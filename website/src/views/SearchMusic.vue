<template>
    <div class="music">
        <h1>Fairy Music</h1>
        <div class="search">
            <input v-model="searchQuery" @keyup.enter="searchMusic" placeholder="搜索音乐..." />
            <button @click="searchMusic" :disabled="isLoading">搜索</button>
        </div>
        <div v-if="isLoading" class="loading">
            正在搜索...
        </div>
        <div v-else-if="playlist.length > 0" class="playlist">
            <h2>播放列表</h2>
            <ul>
                <li v-for="(song, index) in playlist" :key="index" @click="playSong(song)">
                    {{ song.title }} - {{ song.artist }}
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
import api from '../apis/';

const searchQuery = ref('');
const playlist = ref<Array<{ title: string; artist: string }>>([]);
const searchPerformed = ref(false);
const isLoading = ref(false);

const searchMusic = async () => {
    searchPerformed.value = true;
    isLoading.value = true;
    
    try {
        console.log(searchQuery.value);
        api.instance.get("/api/v1/fairy/music/search?keyword=高进", {});
    } catch (error) {
        console.error('error fetching music:', error);
        playlist.value = [];
    } finally {
        isLoading.value = false;
    }
};

const playSong = (song: { title: string; artist: string }) => {
    console.log(`正在播放: ${song.title} - ${song.artist}`);
};
</script>

<style scoped>

</style>