package com.striveonger.music.fairy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.striveonger.music.fairy.*", "com.striveonger.common.**"})
public class AppLaunch {

    public static void main(String[] args) {
        SpringApplication.run(AppLaunch.class, args);
    }

}
