<template>

    <div class="banner">
        <span class="logo">
            <img width="40" height="32" src="/logo.svg" alt="Logo" />
        </span>

        <div class="search" role="search">
            <input type="search" class="form-control" v-model="searchQuery" @keydown.enter="searchMusic(searchQuery)" placeholder="搜索音乐..." aria-label="Search">
        </div>
    </div>

    <div class="music">
        <div v-if="loading" class="loading">
            正在搜索...
        </div>
        <div v-else-if="playlist.length > 0" class="playlist">
            <!-- <h4>播放列表</h4> -->
            <ul>
                <template v-for="(song, index) in playlist" :key="index">
                    <li @click="playSong(song)" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight"
                        aria-controls="offcanvasRight">
                        <img :src="song.cover" :title="song.title" />
                        <span>{{ song.title }}</span>
                    </li>
                </template>
            </ul>
        </div>
        <div v-else-if="searchPerformed" class="no-results">
            没有找到相关音乐
        </div>
        <PlayList :url="url"></PlayList>
        <PlayMusic v-if="store.currentIndex >= 0"></PlayMusic>
    </div>
</template>
<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { search } from '@/apis/Music'
import { SearchItem } from '@/types';
import PlayList from '@/views/backup/PlayList.vue';
import PlayMusic from './PlayMusic.vue';

import { usePlayListStore} from '@/store';
const store = usePlayListStore();

const searchQuery = ref('');
const searchPerformed = ref(false);
const loading = ref(false);
const page = ref(1);
const playlist = ref<Array<SearchItem>>([]);
const url = ref('');

const searchMusic = async (val) => {
    searchPerformed.value = true;
    loading.value = true;
    try {
        console.log('val :>> ', val);
        const result = await search(val, page.value);
        console.log("data: {}", result);
        playlist.value = result;
    } catch (error) {
        console.error('error fetching music:', error);
        playlist.value = [];
    } finally {
        loading.value = false;
    }
};


const playSong = (song: SearchItem) => {
    console.log(`正在跳转到播放页面，歌曲信息:`, song);
    if (url.value != song.url) {
        url.value = song.url;
    }
};

onMounted(() => {
    searchMusic("8090 分P");
});
</script>

<style scoped lang="scss">
.music {
    padding-top: 65px;
    padding-bottom: 60px;
}

.playlist {
    // 设置容器宽度并居中
    // width: 95%;
    // margin: 0 auto;
    justify-content: center;
    margin-left: auto;
    margin-right: auto;

    ul {
        list-style-type: none;
        padding: 0;
        margin-left: 10px;
        margin-right: 10px;
        display: flex;
        flex-wrap: wrap;
        column-gap: 10px;
        row-gap: 10px;
        align-items: flex-end;

        li {
            display: flex;
            flex-direction: column;
            /* margin: 10px; */
            cursor: pointer;
            // 我想获取屏幕的最大宽度
            // width: 230px;
            width: min(max(200px, 17%), 50%);
            /* height: calc(max(200px, 24%)* 0.6); */
            // flex: 1 0 auto;
            align-items: center;
            

            img {
                width: 100%;
                object-fit: cover;
                display: block;
                aspect-ratio: 16 / 9;
            }

            span {
                text-align: center;
                max-width: 100%;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
        }
    }
}

.banner {
    height: 55px;
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

// 设置播放列表的宽度
.offcanvas {
    width: 100%; // 手机屏
    padding-top: 50px;
}

@media (min-width: 600px) and (max-width: 959px) {
    .offcanvas {
        width: 70%; // 平板竖屏时，占屏幕宽度的70%
    }
    .search {
        padding-right: 60px;
    }
}

@media (min-width: 960px) and (max-width: 1365px) {
    .offcanvas {
        width: 60%; // 平板横屏或电脑时，占屏幕宽度的60%
    }
    .search {
        padding-right: 60px;
    }
}

@media (min-width: 1366px) {
    .offcanvas {
        width: 40%; // 电脑时，占屏幕宽度的40%
    }
    .search {
        padding-right: 60px;
    }
}
</style>
