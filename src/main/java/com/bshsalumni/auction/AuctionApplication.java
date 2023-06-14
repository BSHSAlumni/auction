package com.bshsalumni.auction;

import com.bshsalumni.auction.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
//@EnableWebMvc
@SpringBootApplication
public class AuctionApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfig() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("*").allowedOrigins("*");
//			}
//		};
//	}

	@Override
	public void run(String... args) {
		log.info("Auction application version {} started successfully", Constants.VERSION);
	}
}