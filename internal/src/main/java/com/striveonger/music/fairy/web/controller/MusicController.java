package com.striveonger.music.fairy.web.controller;

import cn.hutool.core.util.StrUtil;
import com.striveonger.common.core.constant.CommonConstant;
import com.striveonger.common.core.result.Result;
import com.striveonger.common.web.holder.WebHolder;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.bilibili.BiliMusic;
import com.striveonger.music.fairy.sources.bilibili.BilibiliPlay;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mr.Lee
 * @since 2024-08-27 23:12
 */
@Tag(name = "Music")
@RestController
public class MusicController {
    private final Logger log = LoggerFactory.getLogger(MusicController.class);

    private final Music<BilibiliPlay> music = new BiliMusic();

    @GetMapping(value = CommonConstant.WEB_API_PREFIX + "/v1/fairy/music/search")
    public Result search(String keyword, Integer page) {
        log.info("search: {}, page: {}", keyword, page);
        page = page == null || page < 1 ? 1 : page;
        if (StrUtil.isBlank(keyword)) {
            return Result.success().data(List.of());
        }
        return Result.success().data(music.search(keyword, page));
    }

    @GetMapping(value = CommonConstant.WEB_API_PREFIX + "/v1/fairy/music/playlist")
    public Result playlist(String url) {
        log.info("playlist: {}", url);
        return Result.success().data(music.playlist(url));
    }

    @GetMapping(value = CommonConstant.WEB_API_PREFIX + "/v1/fairy/music/play")
    public void play(BilibiliPlay play) {
        byte[] bytes = music.play(play);
        WebHolder.preview("xx.mp3", bytes);
    }
}
