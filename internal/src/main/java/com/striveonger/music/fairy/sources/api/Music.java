package com.striveonger.music.fairy.sources.api;

import java.util.List;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 20:01
 */
public interface Music {

    String type();

    /**
     * 转换成可播放音频的对象
     *
     * @param url 可播放的流媒体地址
     * @return
     */
    <T extends Play> List<T> convert(String url);

    <T extends Play> byte[] play(T play);

    /**
     * 搜索得到列表
     */
    List<SearchItem> search(String keyword, int page);

    default List<SearchItem> search(String keyword) {
        return search(keyword, 1);
    }

}
