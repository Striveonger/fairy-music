package com.striveonger.music.fairy.web.controller;

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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
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

    private final Music music = new BiliMusic();

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

    @GetMapping("/v1/fairy/music/play")
    public void test(String url, HttpServletRequest request, HttpServletResponse response) {

        url = "https://api.bilibili.com/x/player/playurl?avid=34738621&cid=60824473&bvid=BV1bb411N77n&qn=127&type=&otype=json&fourk=1&fnver=0&fnval=2000";
        Map<String, List<String>> headers = Map.of(
                "Accept", List.of("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"),
                "Accept-Charset", List.of("UTF-8,*;q=0.5"),
                "Accept-Encoding", List.of("gzip,deflate,sdch"),
                "Accept-Language", List.of("en-US,en;q=0.8"),
                "Referer", List.of("https://www.bilibili.com"),
                "User-Agent", List.of("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.81 Safari/537.36")
        );
        HttpResponse result = HttpRequest.get(url).header(headers, true).execute();
        byte[] bytes = result.bodyBytes();
        JsonNode node = Jackson.toJsonNode(new String(bytes, UTF_8));
        // System.out.println(node);

        node = node.get("data").get("dash").get("audio");
        if (node instanceof ArrayNode array) {
            for (JsonNode o : array) {
                url = o.get("baseUrl").asText();
                break;
            }
        }
        log.info("auto play url: {}", url);
        // url = "https://xy125x38x8x69xy2408y8610y3b10y1300yy105xy.mcdn.bilivideo.cn:4483/upgcxcode/73/44/60824473/60824473-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1728801769&gen=playurlv2&os=mcdn&oi=0&trid=00000bf1207e04dd47ce925f3182577494c0u&mid=0&platform=pc&og=cos&upsig=6294e68b6f980046380a703cb7d71a7b&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform,og&mcdnid=16000167&bvc=vod&nettype=0&orderid=0,3&buvid=&build=0&f=u_0_0&agrr=0&bw=16203&logo=A0008000";
        result = HttpRequest.get(url).header(headers, true).execute();
        if (result.isOk()) {
            bytes = result.bodyBytes();
        }
        FileStreamUtils.preview("xx.mp3", request, response, bytes);
    }



}
