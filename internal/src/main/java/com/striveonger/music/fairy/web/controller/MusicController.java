package com.striveonger.music.fairy.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.striveonger.common.core.Jackson;
import com.striveonger.common.core.result.Result;
import com.striveonger.common.leaf.core.IDGen;
import com.striveonger.common.storage.web.utils.FileStreamUtils;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.bilibili.BiliMusic;
import com.striveonger.music.fairy.sources.bilibili.BilibiliPlay;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Mr.Lee
 * @since 2024-08-27 23:12
 */
@Tag(name = "Music")
@RestController
public class MusicController {
    private final Logger log = LoggerFactory.getLogger(MusicController.class);

    @Resource
    private IDGen fitIDGen;

    private final Music<BilibiliPlay> music = new BiliMusic();

    @GetMapping("/v1/fairy/music/hello")
    public Result hello() {
        log.info("hello");
        return Result.success().message("Hello World!").data(Dict.of("segment", fitIDGen.next("A")).set("snowflake", fitIDGen.next()));
    }

    @GetMapping("/v1/fairy/music/search")
    public Result search(String keyword, Integer page) {
        log.info("search: {}, page: {}", keyword, page);
        page = page == null || page < 1 ? 1 : page;
        if (StrUtil.isBlank(keyword)) {
            return Result.success().data(List.of());
        }
        return Result.success().data(music.search(keyword, page));
    }

    @GetMapping("/v1/fairy/music/cover")
    public void cover(String url, HttpServletRequest request, HttpServletResponse response) {
        HttpResponse result = HttpRequest.get(url).execute();
        byte[] bytes = result.bodyBytes();
        FileStreamUtils.preview("xx.jpg", request, response, bytes);
    }

    @GetMapping("/v1/fairy/music/playlist")
    public Result playlist(String url) {
        log.info("playlist: {}", url);
        return Result.success().data(music.playlist(url));
    }

    @GetMapping("/v1/fairy/music/play")
    public void test(BilibiliPlay play, HttpServletRequest request, HttpServletResponse response) {
        // BilibiliPlay play = BeanUtil.toBean(map, BilibiliPlay.class);
        byte[] bytes = music.play(play);
        FileStreamUtils.preview("xx.mp3", request, response, bytes);
    }



}
