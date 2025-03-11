import { computed, ref } from "vue"
import { defineStore } from "pinia";
import { LinkNode, PlayModeEnum, Play, BilibiliPlay } from "@/types";

/**
 * 播放控制
 */
export const playControls = defineStore('playControls', () => {
    /**
     * 播放模式
     */
    const orderMode = new LinkNode(PlayModeEnum.Order);
    const randomMode = new LinkNode(PlayModeEnum.Random);
    const singleMode = new LinkNode(PlayModeEnum.Single);
    orderMode.next = randomMode;
    randomMode.next = singleMode;
    singleMode.next = orderMode;
    const mode = ref(orderMode);

    let list: Array<string> = [];
    let map: Record<string, Play> = {};


    let index = -1;
    const playKey = ref<string>("");
    const isPlaying = ref<boolean>(false);

    const title = computed(() => {
        let x = map[playKey.value] as BilibiliPlay;
        if (x) {
            return x.title;
        } else {
            return "Fairy Music";
        }
    });

    const url = computed(() => {
        let x = map[playKey.value] as BilibiliPlay;
        if (x) {
            // isPlaying.value = true;
            return (import.meta.env.BASE_URL || '') + `api/v1/fairy/music/play?type=${x.type}&bvid=${x.bvid}&aid=${x.aid}&cid=${x.cid}`;
        } else {
            return "";
        }
    });
    const loop = computed(() => {
        return mode.value.value === PlayModeEnum.Single;
    });

    const prev = () => {
        if (mode.value.value === PlayModeEnum.Random) {
            random();
        } else {
            index--;
            if (index <= 0) {
                index = list.length - 1;
            }
            playKey.value = list[index];
        }
    };

    const next = () => {
        if (mode.value.value === PlayModeEnum.Random) {
            random();
        } else {
            index++;
            if (index >= list.length) {
                index = 0;
            }
            playKey.value = list[index];
        }
    };

    const random = () => {
        let i = Math.floor(Math.random() * list.length);
        playKey.value = list[i];
    };

    const clear = () => {
        list = [];
        map = {};
        index = -1;
    };

    const remove = (key: string) => {
        let x = map[key];
        if (x) {
            delete map[key];
            let i = list.indexOf(key);
            if (i >= 0) {
                list.splice(i, 1);
            }
            if (key === playKey.value) {
                next();
            }
        }
    };

    // ===================================================================

    let audio = null;
    const palyProgress = ref<number>(0);
    const init = (o: any) => {
        audio = o;
        audio.value.addEventListener("play", () => isPlaying.value = true);
        audio.value.addEventListener("pause", () => isPlaying.value = false);
        audio.value.addEventListener("timeupdate", () => {
            palyProgress.value = audio.value.currentTime / audio.value.duration * 100
            if (audio.value.currentTime == audio.value.duration) {
                next();
            }
        });
    };

    const isPlay = () => {
        return isPlaying.value;
    };

    const push = (item: Play) => {
        let x = map[item.key]
        if (x) {
            return;
        }
        list.push(item.key);
        map[item.key] = item;
    };

    const onPlay = (o: any) => {
        let key = null;
        if (typeof o === 'number') {
            let i = o as number;
            if (i >= 0 && i < list.length) {
                key = list[i];
            }
        } else if (typeof o === 'string') {
            key = o as string;
        }
        if (key !== null && key !== playKey.value) {
            playKey.value = key;
            isPlaying.value = true;
        }
    };

    const onPlayOrPause = () => {
        if (audio && audio.value && playKey.value !== "") {
            if (audio.value.paused) {
                audio.value.play();
                isPlaying.value = true;
            } else {
                audio.value.pause();
                isPlaying.value = false;
            }
        }
    };

    const getMode = () => {
        return mode.value.value;
    }

    const changeMode = () => {
        mode.value = mode.value.next;
    }

    const getList = () => {
        let result: Play[] = [];
        for (let i = list.length - 1; i >= 0; i--) {
            result.push(map[list[i]]);
        }
        return result;
    }

    return { title, url, loop, playKey, prev, next, remove, push, init, isPlay, onPlay, onPlayOrPause, getMode, changeMode, getList };
});