export type Number = number|null;
export type DateTime = Date|null;

export interface Car {
    id: string,
    name: string,
    price: Number         // 价格
    createTime: DateTime, // 发布时间
    updateTime?: DateTime // 改款时间
}

