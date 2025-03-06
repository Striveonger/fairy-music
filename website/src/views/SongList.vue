<template>
    <div class="song-list">
        <template v-for="(song, index) in list" :key="index">
            <div class="song-item" @click="push(song)">
                {{ song.title }}
            </div>
            <hr class="line">
        </template>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Play } from '@/types';
import { playControls } from '@/store';

const props = defineProps<{ list: Play[] }>();

const init = () => {};
const controls = playControls();
const list = ref<Array<Play>>([]);

list.value = props.list;

const push = (song: Play) => {
    console.log('song :>> ', song);
    controls.push(song);
    document.title = song.title;
}

// const play = (song: Play) => {
//     console.log('song :>> ', song);
//     document.title = song.title;
// }

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
}
</style>