export type Number = number|null;
export type DateTime = Date|null;

export interface Car {
    id: string,
    name: string,
    price: Number         // 价格
    createTime: DateTime, // 发布时间
    updateTime?: DateTime // 改款时间
}

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
    title: string
}

export interface BilibiliPlay extends Play {
    bvid: string,
    aid: string,
    cid: string
}