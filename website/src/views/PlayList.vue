<template>
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight">
        <div class="offcanvas-header">
            <h4 class="offcanvas-title">正在播放:</h4>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <div class="play-music-container">
                <div class="playlist">
                    <template v-for="(song, index) in list" :key="index">
                        <div class="song" :class="{ active: index == current }" @click="save(song, index)">
                            {{ song.title}}
                        </div>
                        <hr class="line">
                    </template>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { playlist } from "@/apis/Music";
import { BilibiliPlay, Play } from '@/types';
import { usePlayStore } from '@/store';

const props = defineProps<{ url: string }>();
const list = ref<Play[]>([]);
// const playUrl = ref('');
const current = ref<Number>(-1);
const store = usePlayStore();

const load = async () => {
    console.log('url :>> ', props.url);
    try {
        list.value = [];
        current.value = -1;
        let result = await playlist(props.url);
        console.log('result :>> ', result);
        list.value = result;
    } catch (error) {
        console.error('Error fetching playlist:', error);
        list.value = [];
    }
}
const save = (song: Play, index: number) => {
    console.log('song :>> ', song);
    current.value = index;
    document.title = song.title;
    if (song.type == "bilibili") {
        store.play.song = song;
        store.play.index = index;
        store.play.source = props.url;
        let x = song as BilibiliPlay;
        store.play.url = `/api/v1/fairy/music/play?type=${x.type}&bvid=${x.bvid}&aid=${x.aid}&cid=${x.cid}`;
    }
}
onMounted(load);
watch(() => props.url, load)
</script>

<style scoped lang="scss">
.play-music-container {
    padding-bottom: 80px;
}

// 添加水平线的样式
.line {
    border: 0;
    height: 1px;
    background-image: linear-gradient(to right, rgba(0, 0, 0, 0), rgba(140, 192, 222, 0.75), rgba(0, 0, 0, 0));
    margin: 0 0;
}

.playlist {
    margin: 10px;
    .song {
        cursor: pointer;
        margin: 2px 3px;
        padding: 5px;
        font-size: 20px;
        font-family: 'Hannotate SC';
        font-weight: bold;
    }
    .active {
        padding-left: 23px;
        border-radius: 7px;
        backdrop-filter: blur(7px);
        background-color: #8CC0DE;
        box-shadow: #B7E0FF 0px 0px 14px 7px;
        border: 2px #8CC0E3 solid;
    }
}
</style>