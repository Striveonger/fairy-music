package com.striveonger.music.fairy.sources.api;

import java.util.List;
import java.util.function.Function;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 20:01
 */
public interface Music {

    String type();

    /**
     * 转换成可播放音频数地址
     * @param url 可播放的流媒体地址
     * @param store 存储流媒体文件的函数
     * @return
     */
    String convert(String url, Function<String, Boolean> store);

    /**
     * 搜索得到列表
     */
    List<SearchItem> search(String keyword);

}
