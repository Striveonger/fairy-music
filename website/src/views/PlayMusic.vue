<template>
    <div>
        <h1>URL: {{ $route.params.url }} </h1>
    </div>
</template>

<script setup lang="ts">
// defineProps<{ url: string }>();
</script>

<style scoped>
h1 {
    color: brown;
}
</style>