<template>
    <div class="music container">
        <h1>Fairy Music</h1>
        <div class="search">
            <input v-model="searchQuery" @keydown.enter="searchMusic" placeholder="搜索音乐..." />
            <button @click="searchMusic" :disabled="isLoading">搜索</button>
        </div>
        <div v-if="isLoading" class="loading">
            正在搜索...
        </div>
        <div v-else-if="playlist.length > 0" class="playlist row">
            <h2>播放列表</h2>
            <ul>
                <template v-for="(song, index) in playlist" :key="index" >
                    <VSearchItem :song="song"></VSearchItem>
                </template>
            </ul>
        </div>
        <div v-else-if="searchPerformed" class="no-results">
            没有找到相关音乐
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { search } from '@/apis/Music'
import router from '@/router'
import { SearchItem } from '@/types';
import VSearchItem from '@/components/SearchItem.vue';

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
        console.log("data: {}", result);
        playlist.value = result;
    } catch (error) {
        console.error('error fetching music:', error);
        playlist.value = [];
    } finally {
        isLoading.value = false;
    }
};
</script>

<style scoped lang="scss">
.playlist {
    ul {
        list-style-type: none;
        padding: 0;
        margin-left: 10px;
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
    }
}
.music {
    max-width: 1680px;
    min-width: 430px;
}
</style>