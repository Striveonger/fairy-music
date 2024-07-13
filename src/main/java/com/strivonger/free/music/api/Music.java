package com.strivonger.free.music.api;

import java.util.List;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 20:01
 */
public interface Music {

    String type();

    /**
     * 转换成可播放音频数地址
     * @param url
     * @return
     */
    String convert(String url);

    /**
     * 搜索得到列表
     */
    List<SearchItem> search(String keyword);

}
