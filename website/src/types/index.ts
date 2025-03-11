export interface SearchItem {
    /**
     * 标题
     */
    title: string,
    /**
     * 封面
     */
    cover: string,
    /**
     * 播放地址
     */
    url: string,
    /**
     * 类型
     */
    type: string
}

export interface Play {
    type: string,
    title: string,
    key: string
}

export interface BilibiliPlay extends Play {
    bvid: string,
    aid: string,
    cid: string
}

export class LinkNode<T> {
    constructor(value) {
        this.value = value;
        this.prev = null;
        this.next = null;
    }
    value: T;
    prev: LinkNode<T> | null;
    next: LinkNode<T> | null;
}

export class LinkList<T> {
    head: LinkNode<T> | null;
    tail: LinkNode<T> | null;
    length: number;
}

export enum PlayModeEnum {
    /**
     * 顺序播放
     */
    Order = "bi-repeat",
    /**
     * 随机播放
     */
    Random = "bi-shuffle",
    /**
     * 单曲循环
     */
    Single = "bi-repeat-1"
}

export enum Constant {
    CURRENT_PLAY_URL = "current://play.list"
}