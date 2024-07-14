package com.strivonger.free.music.launch;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.striveonger.common.core.utils.JacksonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class BiliMusicApplicationTests {

	@Test
	public void test() {
		String html = HttpUtil.get("https://www.bilibili.com/video/BV11u4y1x71G/");
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
		if (Objects.nonNull(root)) {
			System.out.println(root);
		}

	}

}
