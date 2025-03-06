import { computed, ref } from "vue"
import { defineStore } from "pinia";
import { LinkNode, PlayModeEnum, Play, BilibiliPlay } from "@/types";

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
            return (import.meta.env.BASE_URL || '') + `api/v1/fairy/music/play?type=${x.type}&bvid=${x.bvid}&aid=${x.aid}&cid=${x.cid}`;
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

/**
 * 播放控制
 */
export const playControls = defineStore('playControls', () => {
    const mode = ref(new LinkNode(PlayModeEnum.Order));
    mode.value.next = new LinkNode(PlayModeEnum.Random).next = new LinkNode(PlayModeEnum.Single).next = mode.value;

    const list = ref<Array<Play>>([]);

    const index = ref(-1);
    const title = computed(() => {
        if (index.value < 0 || index.value >= list.value.length) {
            return "Fairy Music";
        } else {
            return list.value[index.value].title;
        }
    });
    const url = computed(() => {
        if (index.value < 0 || index.value >= list.value.length) {
            return "";
        } else {
            let x = list.value[index.value] as BilibiliPlay;
            return (import.meta.env.BASE_URL || '') + `api/v1/fairy/music/play?type=${x.type}&bvid=${x.bvid}&aid=${x.aid}&cid=${x.cid}`;
        }
    });
    const loop = computed(() => {
        return mode.value.value === PlayModeEnum.Single;
    });

    const getMode = () => {
        return mode.value;
    }

    const changeMode = () => {
        mode.value.value = mode.value.next;
    }

    const prev = () => {
        index.value--;
        if (index.value <= 0) {
            index.value = list.value.length - 1;
        }
    };

    const next = () => {
        index.value++;
        if (index.value >= list.value.length) {
            index.value = 0;
        }
    };

    const random = () => {
        index.value = Math.floor(Math.random() * list.value.length);
    };

    const play = (i: number) => {
        index.value = i;
    };

    const push = (item: Play) => {
        list.value.push(item);
        // TODO: 临时设置为最后一个, 方便播放
        // TODO: 播放列表
        index.value = list.value.length - 1;
    };

    const remove = (index: number) => {
        list.value.splice(index, 1);
    };

    const clear = () => {
        list.value = [];
    }

    return { title, url, loop, prev, next, random, play, push, remove, clear, getMode, changeMode };
});