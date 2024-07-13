package com.strivonger.free.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.strivonger.free.music.*")
public class AppLaunch {

	public static void main(String[] args) {
		SpringApplication.run(AppLaunch.class, args);
	}

}
