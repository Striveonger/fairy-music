<template>
    
    <h1>msg: {{ msg }}</h1>
    <h1>count: {{ count }}</h1>
    <h1>obj.a: {{ obj.a }}</h1>
    <h1>obj.b: {{ obj.b }}</h1>

    <div>
        <audio src=""></audio>
    </div>
    <div class="card">
        <button type="button" @click="incrementCount">incrementCount</button>
        <button type="button" @click="incrementObjA">incrementObjA</button>
    </div> 
    
</template>

<script setup lang="ts">

// 导入 ref
// import { reactive, Ref, ref, toRef, watch, watchEffect } from 'vue';

import {ref, reactive, Ref, toRef} from 'vue';

import { type Car } from '../types';

// 'defineProps' 是一个编译器宏, 不再需要导入
// import { defineProps } from 'vue';

// 定义页面组件的入参() props 接收参数
defineProps<{ msg: string }>();

// ref 定义一个响应式变量(基本类型)
const count = ref(0);
// reactive 定义一个响应式变量(对象类型)
const obj = reactive({ a: 1, b: 2 });

console.log('count', count);
console.log('obj', obj);

function incrementCount() {
    count.value += 2;
}

function incrementObjA() {
    obj.a += 3;
}

// reactive 描述的对象, 默认开启深度监视
// watch(obj, (value) => {
//     console.log("reactive 描述的对象, 改变了...", value);
// });

// ref 默认未开启深度监视
// watch(count, (value) => {
//     console.log('ref 描述的对象, 改变了...', value);
// });

let car = ref<Car>({
    id: "01",
    name: "极克001",
    price: null,
    createTime: null
});

console.log('Car :>> ', car);

let arr: Ref<Array<Car>> = toRef([
    {
        id: "02",
        name: "仰望 u9",
        price: 100_0000,
        createTime: new Date('2024-04-01T00:00:00')
    },
    {
        id: "03",
        name: "汉 DMI",
        price: 18_8000,
        createTime: new Date('2024-01-01T00:00:00')
    }
]);

// arr.value.push(car.value);

// console.log('arr :>> ', arr);

</script>

<style scoped></style>
