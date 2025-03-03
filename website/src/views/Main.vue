<template>
    <div class="app">
        <div class="head-container">
            <span class="logo">
                <img width="40" height="32" src="/logo.svg" alt="Logo" />
            </span>

            <div class="search" role="search">
                <input type="search" class="form-control" v-model="searchQuery" placeholder="搜索音乐..."
                    aria-label="Search" @keydown.enter="searchMusic(searchQuery)">
            </div>
        </div>
        <div class="main-container">

            <div v-if="loading" class="loading">
                <div class="spinner-grow spinner-grow-sm" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>

            <div class="playlist-container" v-show="!isShowSongContainer || windowWidth > 600">
                <div class="playlist-item" v-for="(item, index) in playlist" :key="index" @click="playlistClick(item)">
                    <img :src="item.cover" :title="item.title" />
                    <span>{{ item.title }}</span>
                </div>
            </div>

            <Transition name="fade-slide">
                <div class="song-container" v-show="isShowSongContainer" ref="songContainer">
                    <div class="song-header">
                        <span>
                            播放列表
                        </span>

                    </div>
                    <div class="song-container-close">
                        >
                    </div>
                    <div v-if="isShowSongContainer && !isLoadFinish" class="loading">
                        <div class="spinner-grow spinner-grow-sm" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>

                    <SongList :list="list" v-if="isShowSongContainer && isLoadFinish" />
                </div>
            </Transition>
        </div>
    </div>
</template>

<script setup lang="ts">
// 组件导入
import { ref, onMounted, reactive } from 'vue';

import { search, playlist as apiPlaylist } from '@/apis/Music'
import { SearchItem, Play } from '@/types';
import SongList from '@/views/SongList.vue';


// 组件状态
const songContainer = ref(null);
const palyContainer = ref(null);

const isShowSongContainer = ref(false);
const isLoadFinish = ref(false);


const windowWidth = ref(window.innerWidth);
const searchQuery = ref('');
const loading = ref(false);
const playlist = ref<Array<SearchItem>>([]);
const list = ref<Array<Play>>([]);

let currentUrl = "";


onMounted(() => init());

// 方法定义
const init = async () => {
    // 初始化逻辑
    searchMusic("8090");
    window.addEventListener('resize', handleResize);
};

const handleResize = () => windowWidth.value = window.innerWidth;

const playlistClick = async (item: SearchItem) => {
    if (!isShowSongContainer.value) {
        isShowSongContainer.value = true;
    }
    if (currentUrl === item.url) {
        return;
    }
    isLoadFinish.value = false;
    console.log('itme :>> ', item);
    currentUrl = item.url;
    let result = await apiPlaylist(item.url);
    list.value = result;
    isLoadFinish.value = true;
    console.log('list :>> ', list.value);
};

const searchMusic = async (val: string) => {
    loading.value = true;
    try {
        currentUrl = "";
        isShowSongContainer.value = false;
        playlist.value = [];
        console.log('val :>> ', val);
        const result = await search(val, 1);
        console.log("data: {}", result);
        playlist.value = result;
    } finally {
        loading.value = false;
    }
}
</script>

<style scoped lang="scss">
.app {
    width: 100%;
    min-width: 390px;
    height: 100%;
    padding: 2px;
    margin: 0px;
    background-color: #f5f5f5;
    --v-image-width: 325px;
}

@media screen and (max-width: 600px) {
    .app {
        --v-image-width: 232px;
    }
}

.head-container {
    width: 100%;
    height: 50px;
    display: flex;
    justify-content: space-between; // 将子元素分散对齐
    align-items: center; // 垂直居中对齐
    flex-wrap: wrap;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 2000;
    background-color: rgba(255, 255, 255, 0.8);
    backdrop-filter: blur(5px);
    padding: 7px;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.5);

    .logo {
        display: flex;
        align-items: center;
        margin-left: 8px;
    }

    .search {
        display: flex;
        justify-content: flex-end; // 将搜索框靠右对齐
        flex: 1; // 允许搜索框占据剩余空间
        max-width: 300px; // 设置最大宽度，避免在大屏幕上过宽
    }
}

.main-container {
    padding: 0px;
    margin: 0px;
    margin-top: 50px;
    padding-left: 1.5%;
    padding-right: 1.5%;
    width: 100%;
    height: calc(98vh - 50px);
    display: flex;
    float: left;
    overflow: hidden;

}

.playlist-container {
    width: 100%;
    min-width: 360px;
    height: 100%;
    padding: 5px;
    display: grid;
    // grid-template-columns: repeat(auto-fill, minmax(min(230px, 65%), 1fr));
    grid-template-columns: repeat(auto-fill, var(--v-image-width, 325px));
    overflow: auto;
    justify-content: center; // 新增居中对齐
    margin: 0 auto; // 新增水平居中
    max-width: calc(100% - 10px); // 防止溢出
    grid-gap: 10px;

    .playlist-item {
        width: auto;
        height: auto;
        margin: 7px;
        cursor: pointer;

        img {
            width: 100%;
            object-fit: cover;
            display: block;
            aspect-ratio: 16 / 9;
        }

        span {
            display: block;
            text-align: center;
            max-width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    }
}

.song-container {
    width: 400%;
    height: 100%;
    padding: 5px;
    overflow-x: auto;
}

.fade-slide-enter-from,
.fade-slide-leave-to {
    opacity: 0;
    transform: translateX(20px);
}

.fade-slide-enter-active,
.fade-slide-leave-active {
    transition: all 0.2s ease-out;
}

</style>
