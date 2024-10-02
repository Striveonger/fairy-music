package com.striveonger.music.fairy.launch;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.Command;
import com.striveonger.common.core.Jackson;
import com.striveonger.common.core.Timepiece;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.api.SearchItem;
import com.striveonger.music.fairy.sources.bilibili.BiliMusic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.List;
import java.util.Objects;


public class BiliMusicApplicationTests {

    @Test
    public void analyze() {
        HttpResponse response = HttpRequest.get("https://www.bilibili.com/video/BV1wJ4m1a7tX/").execute();
        String html = response.body();
        // System.out.println(html);
        Document document = Jsoup.parse(html);
        Elements scripts = document.select("script");
        ObjectNode root = null;
        for (Element script : scripts) {
            String text = script.html();
            if (text.startsWith("window.__playinfo__=")) {
                root = Jackson.toObjectNode(text.substring(20));
                break;
            }
        }
        if (Objects.isNull(root)) {
            return;
        }

        System.out.println(root);
        String targetUrl = null;
        JsonNode node = root.get("data").get("dash").get("audio");
        if (node instanceof ArrayNode array) {
            for (JsonNode o : array) {
                targetUrl = o.get("baseUrl").asText();
                System.out.println(targetUrl);
            }
        }
    }

    @Test
    public void analyze2() {
        String url = "https://www.bilibili.com/video/BV1wJ4m1a7tX/";
        // String url = "https://www.bilibili.com/video/BV1vf421i7hV/";
        HttpResponse response = HttpRequest.get(url).execute();
        String html = response.body();
        // System.out.println(html);

        Document document = Jsoup.parse(html);
        Elements scripts = document.select("script");
        ObjectNode root = null;
        for (Element script : scripts) {
            String text = script.html();
            if (text.startsWith("window.__INITIAL_STATE__=")) {
                root = Jackson.toObjectNode(text.substring(25, text.indexOf(";")));
                break;
            }
        }
        System.out.println(root);
    }

    @Test
    public void lux() {
        Timepiece timepiece = Timepiece.of("RunCommand");
        Command.Result result = Command.of("lux -j https://www.bilibili.com/video/BV1wJ4m1a7tX").run();
        String content = result.getContent();
        JsonNode node = Jackson.toJsonNode(content);
        System.out.println(node);
        timepiece.show();
    }

    @Test
    public void search() {
        String keyword = "小幸运";
        int page = 1;

        Timepiece timepiece = Timepiece.of("SearchMusic");
        Music music = new BiliMusic();
        List<SearchItem> list = music.search(keyword, page);
        System.out.println(Jackson.toJSONString(list));
        timepiece.show();
    }


}
