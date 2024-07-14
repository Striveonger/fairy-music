package com.strivonger.free.music;

import com.striveonger.common.core.web.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.strivonger" })
// @MapperScan("com.strivonger.common.*.mapper")
public class AppLaunch {
	public static void main(String[] args) {
		SpringApplication.run(AppLaunch.class, args);
		// boolean initialize = SpringContextHolder.initialize();
		// System.out.println("initialize = " + initialize);
	}

}
