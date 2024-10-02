<template>
    <div class="music-player">
        <h1>音乐播放器</h1>
        <div class="search-container">
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

const searchQuery = ref('');
const playlist = ref<Array<{ title: string; artist: string }>>([]);
const searchPerformed = ref(false);
const isLoading = ref(false);

const searchMusic = async () => {
    searchPerformed.value = true;
    isLoading.value = true;
    
    try {
        const response = await fetch(`https://api.example.com/search?q=${encodeURIComponent(searchQuery.value)}`);
        if (!response.ok) {
            throw new Error('network response was not ok');
        }
        const data = await response.json();
        playlist.value = data.results.map((item: any) => ({
            title: item.title,
            artist: item.artist
        }));
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
.music-player {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
}

.search-container {
    display: flex;
    margin-bottom: 20px;
}

input {
    flex-grow: 1;
    padding: 10px;
    font-size: 16px;
}

button {
    padding: 10px 20px;
    font-size: 16px;
    background-color: #4CAF50;
    color: white;
    border: none;
    cursor: pointer;
}

.playlist {
    background-color: #f0f0f0;
    padding: 10px;
    border-radius: 5px;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    padding: 10px;
    cursor: pointer;
    transition: background-color 0.3s;
}

li:hover {
    background-color: #e0e0e0;
}

.no-results {
    text-align: center;
    color: #888;
}

.loading {
    text-align: center;
    color: #888;
    margin-top: 20px;
}

button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
}
</style>