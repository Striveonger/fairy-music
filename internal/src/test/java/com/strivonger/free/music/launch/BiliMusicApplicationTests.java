package com.strivonger.free.music.launch;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.utils.JacksonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.Objects;


public class BiliMusicApplicationTests {

	@Test
	public void analyze() {
		String html = HttpUtil.get("https://www.bilibili.com/video/BV1gK411p7L8/");
		System.out.println(html);
		Document document = Jsoup.parse(html);
		Elements scripts = document.select("script");
		ObjectNode root = null;
		for (Element script : scripts) {
			String text = script.html();
			if (text.startsWith("window.__playinfo__=")) {
				root = JacksonUtils.readObjectNode(text.substring(20));
				break;
			}
		}
		if (Objects.isNull(root)) {
			System.out.println(root);
			return;
		}

		JsonNode node = root.get("data").get("dash").get("audio");
		if (node instanceof ArrayNode array) {
			for (JsonNode o : array) {
				System.out.println(o.get("baseUrl").asText());
			}
		}


	}
}
