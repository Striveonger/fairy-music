<template>
    <div class="play-music-container">
        <div class="playlist">
            <template v-for="(song, index) in list" :key="index">
                <div class="song" :class="{ active: index == current }" @click="play(song, index)">{{ song.title }}
                </div>
            </template>
        </div>
        <div class="controls" >
            <audio :src="playUrl" controls autoplay loop></audio>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router'
import { playlist } from "@/apis/Music";
import { BilibiliPlay, Play } from '@/types';
import { i } from 'vite/dist/node/types.d-aGj9QkWt';

// const router = useRouter()
// const route = useRoute()
// let url = router.currentRoute.value.params.url;
// let url = route.params.url;
// console.log('url :>> ', url);
const props = defineProps<{url: string}>();


const list = ref<Play[]>([]);
const playUrl = ref('');
let current: Number = null;

const load = async () => {
    // let url = props.url;
    console.log('url :>> ', props.url);
    // if(!url) {
    //     url = 'https://www.bilibili.com/video/BV1C24y1H7Lw/'
    // }
    try {
        let result = await playlist(props.url);
        console.log('result :>> ', result);
        list.value = result;
    } catch (error) {
        console.error('Error fetching playlist:', error);
        list.value = [];
    }
}
const play = (song: Play, index: Number) => {
    console.log('song :>> ', song);
    current = index;
    if (song.type == "bilibili") {
        let x = song as BilibiliPlay;
        playUrl.value = `/api/v1/fairy/music/play?type=${x.type}&bvid=${x.bvid}&aid=${x.aid}&cid=${x.cid}`
        console.log('playUrl :>> ', playUrl.value);
    }
}
onMounted(load);
watch(()=>props.url, load)
</script>

<style scoped lang="scss">
.play-music-container {
    padding-bottom: 80px;
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

.controls {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    background-color: rgba(255, 255, 255, 0.8);
    backdrop-filter: blur(10px);
    padding: 10px;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);

    audio {
        width: 100%;
        max-width: 600px;
        margin: 0 auto;
        display: block;
    }
}
</style>