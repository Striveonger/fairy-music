<template>
    <div class="controls">
        <audio controls autoplay loop>
            <source :src="store.play.url">
            <!-- <source src="/api/v1/fairy/music/play?type=bilibili&bvid=BV1k3411f7mX&aid=432942112&cid=900040384"> -->
        </audio>
        <!-- <audio src="/api/v1/fairy/music/play?type=bilibili&bvid=BV1k3411f7mX&aid=432942112&cid=900040384" controls autoplay loop></audio> -->
    </div>
</template>

<script setup lang="ts">
import { watch } from 'vue';
import { usePlayStore } from '@/store';
const store = usePlayStore();
// watch(() => store.play.song.title, () => {
//     // window.title = store.play.song.title
//     document.title = store.play.song.title
// });
</script>

<style scoped lang="scss">
.controls {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 2000;
    background-color: rgba(255, 255, 255, 0.8);
    backdrop-filter: blur(10px);
    padding: 10px;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.5);

    audio {
        width: 100%;
        max-width: 600px;
        margin: 0 auto;
        display: block;
    }
}
// audio::-webkit-media-controls-panel

// audio::-webkit-media-controls-mute-button

// audio::-webkit-media-controls-play-button

// audio::-webkit-media-controls-timeline-container

// audio::-webkit-media-controls-current-time-display

// audio::-webkit-media-controls-time-remaining-display

// audio::-webkit-media-controls-timeline

// audio::-webkit-media-controls-volume-slider-container

// audio::-webkit-media-controls-volume-slider

// audio::-webkit-media-controls-seek-back-button

// audio::-webkit-media-controls-seek-forward-button

// audio::-webkit-media-controls-fullscreen-button

// audio::-webkit-media-controls-rewind-button

// audio::-webkit-media-controls-return-to-realtime-button

// audio::-webkit-media-controls-toggle-closed-captions-button
</style>