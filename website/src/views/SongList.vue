<template>
    <div class="song-list">
        <template v-for="(song, index) in list" :key="index">
            <div class="song-item" @click="play(song)">
                {{ song.title }} 
                <div>
                    <!-- <i class="bi bi-play"></i> -->
                    <!-- click.stop 阻止添加按钮的事件冒泡 -->
                    
                    <i v-if="props.since" class="bi bi-trash3" @click.stop="remove(song)" ></i>
                    <i v-else class="bi bi-plus-square" @click.stop="push(song)" ></i>

                </div>
            </div>
            <hr class="line">
        </template>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Play } from '@/types';
import { playControls } from '@/store';

const props = defineProps<{ list: Play[], since: boolean }>();

const init = () => {};
const controls = playControls();
const list = ref<Array<Play>>([]);

list.value = props.list;

const push = (song: Play) => {
    controls.push(song);
}

const play = (song: Play) => {
    console.log('song :>> ', song);
    controls.push(song);
    controls.onPlay(song.key);
}

const remove = (song: Play) => {
    console.log('song :>> ', song);
    controls.remove(song.key);
}

onMounted(() => init());

</script>

<style scoped lang="scss">
.song-list {
    width: 100%;
    height: 100%;
    overflow: auto;
    padding: 10px;
    padding-left: 17px;
    box-sizing: border-box;
    animation: bounceInRight; /* referring directly to the animation's @keyframe declaration */
    animation-duration: 500ms; /* don't forget to set a duration! */  

    .song-item {
        width: 100%;
        height: 30px;
        line-height: 30px;
        font-size: 14px;
        color: #2c2c2c;
        cursor: pointer;

        div {
            float: right;
            margin-right: 3%;
            font-size: 12px;
            i {
                cursor: pointer;
                margin-left: 5px;
                font-size: 1.2rem;
            }
        }
    }
}

</style>