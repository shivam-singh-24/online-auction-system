package com.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // ✅ Enables scheduled tasks like auction expiry automation
public class OnlineAuctionSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineAuctionSystemApplication.class, args);
	}
}
