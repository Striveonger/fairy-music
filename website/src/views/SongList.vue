<template>

    <template v-for="(song, index) in list" :key="index">
        <div class="song-item" :class="{ active: index == 1 }" @click="save(song, index)">
            {{ song.title }}
        </div>
        <hr class="line">
    </template>

</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Play } from '@/types';
import { usePlayListStore } from '@/store';


const props = defineProps<{ list: Play[] }>();

onMounted(() => init());

// 方法定义
const init = async () => {
    // 初始化逻辑
    console.log('init');
    console.log('list :>> ', props.list);
};
const store = usePlayListStore();
const list = ref<Array<Play>>([]);
list.value = props.list;

const save = (song: Play, index: number) => {
    console.log('song :>> ', song);
    store.currentIndex = index;
    document.title = song.title;
}

</script>