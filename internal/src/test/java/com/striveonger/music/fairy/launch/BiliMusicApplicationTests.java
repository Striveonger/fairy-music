package com.striveonger.music.fairy.launch;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.Command;
import com.striveonger.common.core.Jackson;
import com.striveonger.common.core.Timepiece;
import com.striveonger.music.fairy.sources.api.Music;
import com.striveonger.music.fairy.sources.api.SearchItem;
import com.striveonger.music.fairy.sources.bilibili.BiliMusic;
import com.striveonger.music.fairy.sources.bilibili.BilibiliPlay;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;


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
        String url = "https://www.bilibili.com/video/BV1Pr4y1i7pz/";  // 单P
        // String url = "https://www.bilibili.com/video/BV1bb411N77n/"; // 分P
        HttpResponse response = HttpRequest.get(url).execute();
        String html = response.body();
        // System.out.println(html);

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
        System.out.println(root);
    }

    @Test
    public void lux() {
        Timepiece timepiece = Timepiece.of("RunCommand");
        List<String> cmds = List.of("lux", "-j", "https://www.bilibili.com/video/BV1wJ4m1a7tX");
        Command.Result result = Command.of(cmds).run();
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

    @Test
    public void play() {
        String url = "https://api.bilibili.com/x/player/playurl?avid=34738621&cid=60824473&bvid=BV1bb411N77n&qn=127&type=&otype=json&fourk=1&fnver=0&fnval=2000";
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
        System.out.println(node);

        url = "https://xy125x38x8x69xy2408y8610y3b10y1300yy105xy.mcdn.bilivideo.cn:4483/upgcxcode/73/44/60824473/60824473-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1728801769&gen=playurlv2&os=mcdn&oi=0&trid=00000bf1207e04dd47ce925f3182577494c0u&mid=0&platform=pc&og=cos&upsig=6294e68b6f980046380a703cb7d71a7b&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,mid,platform,og&mcdnid=16000167&bvc=vod&nettype=0&orderid=0,3&buvid=&build=0&f=u_0_0&agrr=0&bw=16203&logo=A0008000";
        result = HttpRequest.get(url).header(headers, true).execute();
        System.out.println(result.isOk());
    }

    @Test
    public void bilibiliTest() {
        // String url = "https://www.bilibili.com/video/BV1Pr4y1i7pz/";  // 单P
        String url = "https://www.bilibili.com/video/BV1bb411N77n/";     // 分P
        // String url = "https://www.bilibili.com/video/BV1Mh4y1c7RY/";  // 订阅
        BiliMusic music = new BiliMusic();
        List<BilibiliPlay> list = music.playlist(url);
        System.out.println(Jackson.toJSONString(list));
    }



}
