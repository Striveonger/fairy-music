<template>
    <div class="music">
        <h1>Fairy Music</h1>
        <div class="search">
            <input v-model="searchQuery" @keydown.enter="searchMusic" placeholder="搜索音乐..." />
            <button @click="searchMusic" :disabled="loading">搜索</button>
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
        <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight">
            <div class="offcanvas-header">
                <h4 class="offcanvas-title">正在播放:</h4>
                <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>
            <div class="offcanvas-body">
                <PlayMusic v-if="url" :url="url"></PlayMusic>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import { defineAsyncComponent, ref } from 'vue';
import { search } from '@/apis/Music'
import { SearchItem } from '@/types';
import PlayMusic from '@/views/PlayMusic.vue';

const searchQuery = ref('');
const searchPerformed = ref(false);
const loading = ref(false);
const page = ref(1);
const playlist = ref<Array<SearchItem>>([]);
const play = ref(null);
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
            width: max(min(150px, 45%), 15%);
            /* height: calc(max(200px, 24%)* 0.6); */
            flex: 1 0 auto;
            align-items: center;

            img {
                width: 100%;
                /* height: 0; */
                /* padding-bottom: 65%; */
                /* min-width: 200px; */
                /* min-height: 115px; */
                /* margin-bottom: 10px; */
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

.music {
    max-width: 1680px;
    min-width: 430px;
}
</style>