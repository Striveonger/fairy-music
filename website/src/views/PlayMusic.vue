<template>
    <audio id="audio" :src="store.currentPlayURL" autoplay :loop="isLoop"></audio>
    <div class="controls">
        <!-- 
        <audio autoplay loop>
            <source :src="store.play.url" type="audio/mpeg">
        </audio>
        -->
        <!-- <audio :src="store.play.url" controls autoplay loop></audio> -->
        <div class="layers">
            <div class="title">{{ store.currentTitle }}</div>
            <div class="play-progress" :style="palyProgress"></div>
            <div class="buttons">
                <!-- 控制器 -->
                <i class="bi bi-skip-start-fill" @click="store.prev"></i>
                <i class="bi" :class="isPlaying ? 'bi-pause-fill' : 'bi-play-fill'" @click="onPlayOrPause"></i>
                <i class="bi bi-skip-end-fill" @click="store.next"></i>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue';
import { usePlayListStore } from '@/store';
const store = usePlayListStore();
const isPlaying = ref(true);
const isLoop = ref(false);
const percentage = ref(0);
let audio = null;

const palyProgress = computed(() => {
    return "width:"+ percentage.value +"%;"
});

const onPlayOrPause = () => {
    isPlaying.value = !isPlaying.value;
    if (isPlaying.value) {
        audio.play();
    } else {
        audio.pause();
    }
}

onMounted(() => {
    // 绑定播放和暂停事件 & 进度条
    audio = document.getElementById("audio");
    audio.addEventListener("play", () => isPlaying.value = true);
    audio.addEventListener("pause", () => isPlaying.value = false);
    audio.addEventListener("timeupdate", () => {
        percentage.value = audio.currentTime / audio.duration * 100
        if(!isLoop.value && audio.currentTime == audio.duration) {
            store.next();
        }
    });
    isLoop.value = store.list.length === 0;
});
watch(() => store.list.length, (len) => isLoop.value = len === 0);
watch(() => store.currentTitle, (title) => document.title = title);
</script>

<style scoped lang="scss">
.controls {
    height: 65px;
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 2000;
    background-color: rgba(255, 255, 255, 0.7);
    backdrop-filter: blur(5px);
    padding: 0px;
    box-shadow: 0 -2px 10px rgba(23, 23, 23, 0.3);

    .layers {
        position: relative;
        width: 100%;
        height: 55px;

        div {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            i {
                color: #12121299;
                font-size: 3.1rem;
                padding-left: 0px;
                padding-right: 10px;
                cursor: pointer;
                z-index: 2004;

                &:hover {
                    color: #000;
                }
            }
        }

        .title {
            z-index: 2002;
            margin-top: 10px;
            // font-weight: bold;
            font-size: 0.7rem;
            display: flex;
            align-items: last baseline;
            justify-content: center;
        }

        .buttons {
            display: flex;
            justify-content: center;
            align-items: center; // 垂直居中对齐
            z-index: 2003;
        }

        .play-progress {
            height: 10%;
            background: linear-gradient(to bottom right, rgb(246, 43, 43), rgb(239, 195, 63), rgb(84, 154, 84), rgb(33, 151, 219), rgb(182, 135, 213), rgb(240, 167, 240));
            z-index: 2001;
        }
    }
}

audio {
    display: none;
}

</style>
