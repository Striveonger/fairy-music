package com.striveonger.music.fairy.sources.bilibili;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.Jackson;
import com.striveonger.common.core.Timepiece;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.api.SearchItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Mr.Lee
 * @description:
 * @date 2024-07-02 22:50
 */
public class BiliMusic implements Music<BilibiliPlay> {
    private final Logger log = LoggerFactory.getLogger(BiliMusic.class);
    // 如果利益不能共享, 为何责任要平摊.
    // 如果个人的困苦与集体无关, 那集体的荣辱又与我何干.

    @Override
    public String type() {
        return "bilibili";
    }

    @Override
    public List<SearchItem> search(String keyword, int page) {
        String url = "https://search.bilibili.com/all?keyword=" + keyword;
        if (page > 1) {
            url += "&page=" + page;
        }
        Timepiece timepiece = Timepiece.of("Query Bilibili Music Search");
        String html = HttpUtil.get(url);
        timepiece.mark("Http Request");
        Document document = Jsoup.parse(html);
        timepiece.mark("Html Parse");
        Elements elements = document.select("div.video.i_wrapper.search-all-list > div > div");
        timepiece.mark("Elements Select");
        List<SearchItem> list = elements.stream().map(this::convert).filter(Objects::nonNull).toList();
        timepiece.mark("Convert Result");
        timepiece.show();
        log.info("Bilibili Music search result: {}", list);
        return list;
    }

    @Override
    public List<BilibiliPlay> playlist(String url) {
        Timepiece timepiece = Timepiece.of("Query Bilibili Music Play");
        HttpResponse response = HttpRequest.get(url).execute();
        String html = response.body();
        Document document = Jsoup.parse(html);
        Elements scripts = document.select("script");
        ObjectNode root = null;
        for (Element script : scripts) {
            String text = script.html();
            if (text.startsWith("window.__INITIAL_STATE__=")) {
                String s = text.substring(25, text.lastIndexOf("};")) + "}";
                root = Jackson.toObjectNode(s);
                break;
            }
        }
        JsonNode node = root.get("videoData").get("pages");
        List<BilibiliPlay> list = new ArrayList<>();
        if (node instanceof ArrayNode array) {
            String bvid = root.get("bvid").asText();
            String aid = root.get("aid").asText();
            for (int i = 0; i < array.size(); i++) {
                node = array.get(i);
                String cid = node.get("cid").asText();
                String title = node.get("part").asText();
                BilibiliPlay play = new BilibiliPlay(bvid, aid, cid, title);
                list.add(play);
            }
        }
        // 解析合集中所有视频
        if (CollUtil.isEmpty(list) || list.size() == 1) {
            node = root.get("availableVideoList");
            if (node instanceof ArrayNode array) {
                for (int i = 0; i < array.size(); i++) {
                    node = array.get(i);
                    String bvid = node.get("bvid").asText();
                    String aid = node.get("aid").asText();
                    String cid = node.get("cid").asText();
                    String title = node.get("title").asText();
                    BilibiliPlay play = new BilibiliPlay(bvid, aid, cid, title);
                    list.add(play);
                }
            }
        }
        timepiece.show();
        return list;
    }

    @Override
    public byte[] play(BilibiliPlay play) {
        String url = StrUtil.format("https://api.bilibili.com/x/player/playurl?avid={}&cid={}&bvid={}&qn=127&type=&otype=json&fourk=1&fnver=0&fnval=2000", play.getAid(), play.getCid(), play.getBvid());
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
        node = node.get("data").get("dash").get("audio");
        if (node instanceof ArrayNode array) {
            for (JsonNode o : array) {
                url = o.get("baseUrl").asText();
                break;
            }
        }
        log.info("auto play url: {}", url);
        boolean retry = false;
        int cnt = 3;
        do {
            try {
                result = HttpRequest.get(url).header(headers, true).execute();
                if (result.isOk()) {
                    bytes = result.bodyBytes();
                }
            } catch (Exception e) {
                log.error("auto play url: {}", url);
                retry = true;
            }
        } while (retry && --cnt > 0);
        return bytes;
    }

    private SearchItem convert(Element element) {
        Element a = element.select(".bili-video-card__wrap > a").first(), img;
        if (a == null || (img = a.select("img").first()) == null) {
            return null;
        }
        SearchItem item = new SearchItem();
        item.setUrl("https:" + a.attr("href"));
        item.setCover("https:" + img.attr("src"));
        item.setTitle(img.attr("alt"));
        item.setType(type());
        return item;
    }
}
