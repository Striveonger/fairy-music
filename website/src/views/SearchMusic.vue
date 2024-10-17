<template>
    <div class="music">
        <h1>Fairy Music</h1>
        <div class="search">
            <input v-model="searchQuery" @keydown.enter="searchMusic" placeholder="搜索音乐..." />
        </div>
        <div v-if="loading" class="loading">
            正在搜索...
        </div>
        <div v-else-if="playlist.length > 0" class="playlist">
            <h2>播放列表</h2>
            <ul>
                <template v-for="(song, index) in playlist" :key="index">
                    <li @click="playSong(song)" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight"
                        aria-controls="offcanvasRight">
                        <img :src="coverProxy(song)" :title="song.title" />
                        <span>{{ song.title }}</span>
                    </li>
                </template>
            </ul>
        </div>
        <div v-else-if="searchPerformed" class="no-results">
            没有找到相关音乐
        </div>
        <PlayList :url="url"></PlayList>
        <PlayMusic v-if="store.play.song" ></PlayMusic>
    </div>
</template>
<script setup lang="ts">
import { defineAsyncComponent, ref } from 'vue';
import { search } from '@/apis/Music'
import { SearchItem } from '@/types';
import PlayList from '@/views/PlayList.vue';
import PlayMusic from './PlayMusic.vue';

import { usePlayStore } from '@/store';
const store = usePlayStore();

const searchQuery = ref('');
const searchPerformed = ref(false);
const loading = ref(false);
const page = ref(1);
const playlist = ref<Array<SearchItem>>([]);
const url = ref('');

const searchMusic = async () => {
    searchPerformed.value = true;
    loading.value = true;
    try {
        const result = await search(searchQuery.value, page.value);
        console.log("data: {}", result);
        playlist.value = result;
    } catch (error) {
        console.error('error fetching music:', error);
        playlist.value = [];
    } finally {
        loading.value = false;
    }
};

const coverProxy = (song: SearchItem) => {
    // console.log('song :>> ', song);
    return "/api/v1/fairy/music/cover?url=" + song.cover;
}

const playSong = (song: SearchItem) => {
    console.log(`正在跳转到播放页面，歌曲信息:`, song);
    if (url.value != song.url) {
        url.value = song.url;
        // play.value = defineAsyncComponent(() => {
        //     return new Promise((resolve, reject) => {
        //         import("@/views/PlayMusic.vue").then((module) => {
        //             const component = module.default;
        //             component.props = {
        //                 url: song.url
        //             };
        //             resolve(component);
        //         });
        //     });
        // });
    }
};
</script>

<style scoped lang="scss">
.playlist {
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
            width: max(min(200px, 35%), 20%);
            /* height: calc(max(200px, 24%)* 0.6); */
            flex: 1 0 auto;
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

// 设置播放列表的宽度
.offcanvas {
    width: 100%; // 手机屏
}

@media (min-width: 600px) and (max-width: 959px) {
    .offcanvas {
        width: 70%; // 平板竖屏时，占屏幕宽度的70%
    }
}

@media (min-width: 960px) and (max-width: 1365px) {
    .offcanvas {
        width: 60%; // 平板横屏或电脑时，占屏幕宽度的60%
    }
}

@media (min-width: 1366px) {
    .offcanvas {
        width: 40%; // 电脑时，占屏幕宽度的40%
    }
}
</style>