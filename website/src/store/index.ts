import { computed, ref } from "vue"
import { defineStore } from "pinia";
import { Play, BilibiliPlay } from "@/types";
import { publicDecrypt } from "crypto";

export const usePlayListStore = defineStore('playliststore', () => {
    const list = ref<Play[]>([]);
    const url = ref("");
    const currentIndex = ref(-1);
    const currentTitle = computed(() => {
        if (currentIndex.value < 0 || currentIndex.value >= list.value.length) {
            return "";
        } else {
            return list.value[currentIndex.value].title;
        }
    });
    const currentPlayURL = computed(() => {
        console.log('currentIndex :>> ', currentIndex);
        if (currentIndex.value < 0 || currentIndex.value >= list.value.length) {
            return "";
        } else {
            let x = list.value[currentIndex.value] as BilibiliPlay;
            return `/api/v1/fairy/music/play?type=${x.type}&bvid=${x.bvid}&aid=${x.aid}&cid=${x.cid}`;
        }
    });

    const next = () => {
        currentIndex.value++;
        if (currentIndex.value >= list.value.length) {
            currentIndex.value = 0;
        }
    }

    const prev = () => {
        currentIndex.value--;
        if (currentIndex.value <= 0) {
            currentIndex.value = list.value.length - 1;
        }
    }

    return { list, url, currentIndex, currentTitle, currentPlayURL, next, prev };
});
