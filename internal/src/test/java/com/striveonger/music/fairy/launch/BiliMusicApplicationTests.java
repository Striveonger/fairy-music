package com.striveonger.music.fairy.launch;

import cn.hutool.core.lang.Pair;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.Jackson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class BiliMusicApplicationTests {

    @Test
    public void analyze() {
        HttpResponse response = HttpRequest.get("https://www.bilibili.com/video/BV1gK411p7L8/").execute();
        // System.out.println(html);
        Document document = Jsoup.parse(response.body());
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

        List<Pair<String, String>> keys = List.of(
                Pair.of("Transfer-Encoding", "Transfer-Encoding"),
                Pair.of("X-Cache-Webcdn", "X-Cache-Webcdn"),
                Pair.of("Content-Encoding", "Content-Encoding"),
                Pair.of("Connection", "Connection"),
                Pair.of("IDC", "IDC"),
                Pair.of("Vary", "Vary"),
                Pair.of("Set-Cookie", "Cookie"),
                Pair.of("server-timing", "server-timing"),
                Pair.of("gear", "gear"));

        Map<String, List<String>> headers = new HashMap<>();
        for (Pair<String, String> key : keys) {
            headers.put(key.getValue(), List.of(response.header(key.getKey())));
        }

        String targetUrl = null;
        JsonNode node = root.get("data").get("dash").get("audio");
        if (node instanceof ArrayNode array) {
            for (JsonNode o : array) {
                targetUrl = o.get("baseUrl").asText();
                System.out.println(targetUrl);
            }
        }
        if (Objects.isNull(targetUrl)) {
            return;
        }

        HttpResponse httpResponse = HttpRequest.get(targetUrl).header(headers).execute();
        System.out.println(httpResponse.isOk());


        // System.out.println(JacksonUtils.toJSONString(headers));


    }
}
