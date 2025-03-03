<template>
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight">
        <div class="offcanvas-header">
            <h4 class="offcanvas-title">正在播放:</h4>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <div class="play-music-container">
                <div class="playlist">
                    <template v-for="(song, index) in playListStore.list" :key="index">
                        <div class="song" :class="{ active: index == playListStore.currentIndex }" @click="save(song, index)">
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
import { Play } from '@/types';
import { usePlayListStore } from '@/store';

const props = defineProps<{ url: string }>();
// const list = ref<Play[]>([]);
// const playUrl = ref('');
// const current = ref<Number>(-1);
const playListStore = usePlayListStore();

const load = async () => {
    console.log('url :>> ', props.url);
    try {
        playListStore.list = [];
        playListStore.currentIndex = -1;
        let result = await playlist(props.url);
        console.log('result :>> ', result);
        // list.value = result;
        // list to pinia
        playListStore.list = result;
        playListStore.url = props.url;
    } catch (error) {
        console.error('Error fetching playlist:', error);
        playListStore.list = [];
        playListStore.currentIndex = -1;
    }
}
const save = (song: Play, index: number) => {
    console.log('song :>> ', song);
    playListStore.currentIndex = index;
    document.title = song.title;
}
onMounted(load);
watch(() => props.url, load);
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