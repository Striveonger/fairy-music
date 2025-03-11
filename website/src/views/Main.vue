<template>
    <div class="app">
        <div class="head-container">
            <span class="logo">
                <a :href="baseUrl"><img width="40" height="32" src="/logo.svg" alt="Logo" /></a>
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

            <div class="playlist-container" ref="palyContainer" v-show="!loading && (!isShowSongContainer || windowWidth > 600)">
                <div class="playlist-item" :data-key="index" v-for="(item, index) in playlist" :key="index"
                    @click="playlistClick(item, index, $event)">
                    <img :src="item.cover" :title="item.title" />
                    <span>{{ item.title }}</span>
                </div>
            </div>

            <div class="song-container" v-show="isShowSongContainer" :ref="songContainer">
                <div class="song-container-close" v-if="isShowSongContainer && windowWidth <= 600" @click="closeSongContainer()">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-chevron-compact-right"
                        viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                            d="M6.776 1.553a.5.5 0 0 1 .671.223l3 6a.5.5 0 0 1 0 .448l-3 6a.5.5 0 1 1-.894-.448L9.44 8 6.553 2.224a.5.5 0 0 1 .223-.671" />
                    </svg>
                </div>

                <div v-if="isShowSongContainer && !isLoadFinish" class="loading">
                    <div class="spinner-grow spinner-grow-sm" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                </div>

                <SongList :list="list" :since="since"  v-if="isShowSongContainer && isLoadFinish" />
            </div>

            <div class="controls">
                <audio ref="audio" autoplay :loop="controls.loop" :src="controls.url"></audio>
                <div class="controls-container">

                    <i class="bi foo" :class="controls.getMode()" @click="controls.changeMode()"></i>
                
                    <i class="bi foo bi-skip-start-fill" @click="controls.prev()"></i>
                    <i class="bi" :class="controls.isPlay() ? 'bi-pause-fill' : 'bi-play-fill'" @click="controls.onPlayOrPause()" ></i>
                    <i class="bi foo bi-skip-end-fill" @click="controls.next()"></i>
                    <i class="bi foo bi-music-note-list" @click="sincePlayList()"></i>
                    <!-- 
                    <i class="bi bi-cursor-fill"></i>
                    <i class="bi bi-volume-up"></i>
                    <i class="bi bi-volume-mute"></i>
                    <i class="bi bi-volume-down"></i>
                    <i class="bi bi-volume-off"></i>
                    <i class="bi bi-volume-high"></i>
                    <i class="bi bi-volume-low"></i>
                    -->
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
// 组件导入
import { ref, onMounted, nextTick } from 'vue';

import { search, playlist as apiPlaylist } from '@/apis/Music'
import { SearchItem, Play } from '@/types';
import { playControls } from '@/store';
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

const since = ref<boolean>(false);
const list = ref<Array<Play>>([]);


const controls = playControls();
const audio = ref(null);

let currentUrl = "";

const baseUrl = ref(import.meta.env.BASE_URL);

// 方法定义
const init = async () => {
    // 初始化逻辑
    searchMusic("8090");
    window.addEventListener('resize', handleResize);
    controls.init(audio);
    document.title = controls.title;
};

onMounted(init);

const handleResize = () => windowWidth.value = window.innerWidth;

const playlistClick = (item: SearchItem, index: number, event: any) => {
    if (!isShowSongContainer.value) {
        isShowSongContainer.value = true;
    }
    if (currentUrl === item.url) {
        closeSongContainer();
        return;
    }
    since.value = false;
    isLoadFinish.value = false;
    currentUrl = item.url;
    nextTick(async () => {
        // palyContainer.value.scrollTop = index * 188;
        palyContainer.value.scrollTo({
            top: index * 188,
            behavior: 'smooth'
        });
        let result = await apiPlaylist(item.url);
        list.value = result;
        isLoadFinish.value = true;
    });
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

const closeSongContainer = () => {
    isShowSongContainer.value = false;
    currentUrl = "";
    since.value = false;
    isLoadFinish.value = false;
    list.value = [];
}

const sincePlayList = () => {
    isLoadFinish.value = false;
    isShowSongContainer.value = true;
    list.value = [];
    currentUrl = "";
    since.value = true;
    nextTick(() => {
        let result = controls.getList();
        list.value = result;
        isLoadFinish.value = true;
    });
}
</script>

<style scoped lang="scss">
.app {
    width: 100%;
    min-width: 390px;
    height: 100%;
    padding: 0px;
    margin: 0px;
    background-color: #f5f5f5;

    --v-image-width: 263px;
    --animate-duration: 350ms;
    --animate-delay: 0.5s;
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
    // padding-left: 1.5%;
    // padding-right: 1.5%;
    width: 100%;
    height: calc(98vh - 50px);
    display: flex;
    float: left;
    overflow: hidden;

}

.playlist-container {
    width: 100%;
    min-width: 292px;
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

    animation: bounceInLeft;
    /* referring directly to the animation's @keyframe declaration */
    animation-duration: 500ms;
    /* don't forget to set a duration! */

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
    overflow-x: auto;
    padding-top: 15px;

    animation: bounceInRight;
}

.song-container-close {
    position: absolute;
    z-index: 1000;
    top: calc(50% + 12px);
    cursor: pointer;
    transition: all 0.3s ease;
    background: rgba(134, 134, 134, 0.835);
    color: #fff;
    width: 21px;
    height: 90px;
    border-radius: 0 7px 7px 0;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);

    svg {
        position: absolute;
        left: -16px;
        width: 49px;
        height: 100%;
    }
}

// .song-container-close:hover {
//     color: #333;
//     transform: rotate(90deg) scale(1.1);
//     background: rgba(255, 255, 255, 1);
// }

// .song-container-close:active {
//     transform: rotate(90deg) scale(0.9);
// }


.controls {
    // 边框
    border: 1px solid #ccc;
    // 圆角
    border-radius: 65px;
    // 阴影
    box-shadow: 0 0 5px #ccc;
    // 背景颜色
    background-color: #fff;
    padding-left: 10px;
    padding-right: 10px;
    padding-top: 3px;
    padding-bottom: 3px;

    // 定位
    position: absolute;
    z-index: 1000;
    right: 30px;
    bottom: 30px;
    // display: flex;
    // align-items: center;
    i {
        cursor: pointer;
        // margin: 0 5px;
        padding-top: 5px;
        padding-left: 5px;
        padding-right: 5px;
        padding-bottom: 5px;
        font-size: 1.5em;
    }

    .foo {
        display: none;
        // color: #ccc;
        // opacity: 0;
        // transform: translateX(20px);
        // transition: all 0.3s ease-in-out;
        // display: inline; // 保持元素可交互性
        // margin-left: -5px; // 修复动画过程中的间距问题
        // margin-right: -5px; // 修复动画过程中的间距问题
    }
}

.controls:hover {
    .foo {
        // opacity: 1;
        // transform: translateX(0);
        // transition-delay: 0.1s;
        // display: block; // 竖向排列
        display: inline;

    }
    // i {
    //     background-color: #292929;
    //     // border-color: #292929;
    // }
}



</style>