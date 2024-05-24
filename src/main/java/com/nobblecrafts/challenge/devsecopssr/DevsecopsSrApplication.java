package com.nobblecrafts.challenge.devsecopssr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class DevsecopsSrApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevsecopsSrApplication.class, args);
	}
}
