package com.strivonger.free.music.sources.bilibili;

import com.strivonger.free.music.sources.api.Music;
import com.strivonger.free.music.sources.api.SearchItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 22:50
 */
public class BiliMusic implements Music {
    private final Logger log = LoggerFactory.getLogger(BiliMusic.class);

    @Override
    public String type() {
        return "Bilibili";
    }

    @Override
    public String convert(String url, Function<String, Boolean> store) {
        return "";
    }

    @Override
    public List<SearchItem> search(String keyword) {
        return List.of();
    }
}
