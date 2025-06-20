package com.angkorchat.emoji.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@MapperScan("com.angkorchat.emoji.cms.domain")
@SpringBootApplication
public class AngkorEmojiCmsApplication {

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(AngkorEmojiCmsApplication.class, args);
	}

}
