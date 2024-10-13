package com.striveonger.music.fairy.sources.api;

import java.util.List;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 20:01
 */
public interface Music<T extends Play> {

    String type();

    /**
     * 获取播放列表
     * @param url 可播放的流媒体地址
     * @return 可播放的音频对象
     */
    List<T> playlist(String url);

    /**
     * 播放音频
     * @param play 可播放的音频对象
     * @return 可播放的音频字节流
     */
    byte[] play(T play);

    /**
     * 搜索得到列表
     */
    List<SearchItem> search(String keyword, int page);

    default List<SearchItem> search(String keyword) {
        return search(keyword, 1);
    }

}
