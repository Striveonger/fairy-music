import { computed, ref } from "vue"
import { defineStore } from "pinia";
import { PlayStore, BilibiliPlay } from "@/types";


export const usePlayStore = defineStore('playstore', () => {
    const play = ref<PlayStore>({
        song: null,
        index: -1,
        url: "",
        source: ""
    });
    // const url = computed(() => {
    //     if (play.value.song.type == "bilibili") {
    //         let x = play.value.song as BilibiliPlay;
    //         return `/api/v1/fairy/music/play?type=${x.type}&bvid=${x.bvid}&aid=${x.aid}&cid=${x.cid}`;
    //     }
    //     return "";
    // });
    return { play };
});

