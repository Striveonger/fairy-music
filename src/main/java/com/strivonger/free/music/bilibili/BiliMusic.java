package com.strivonger.free.music.bilibili;

import com.strivonger.free.music.api.Music;
import com.strivonger.free.music.api.SearchItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 22:50
 */
@Service
public class BiliMusic implements Music {
    private final Logger log = LoggerFactory.getLogger(BiliMusic.class);

    @Override
    public String type() {
        return "Bilibili";
    }

    @Override
    public String convert(String url) {
        return "";
    }

    @Override
    public List<SearchItem> search(String keyword) {
        return List.of();
    }
}
