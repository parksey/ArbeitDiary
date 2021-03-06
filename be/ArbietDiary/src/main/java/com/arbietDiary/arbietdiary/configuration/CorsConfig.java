package com.arbietDiary.arbietdiary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
public class CorsConfig {
	//@Bean
	public CorsFilter corsFilter() {
		System.out.println("[Bean등록: CorsFilter]");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true); // 서버 - JS JSON
//		config.addAllowedOrigin("http://localhost:3000");
//		config.addAllowedOrigin("http://54.180.70.202:8080");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.addExposedHeader("Authorization");
		source.registerCorsConfiguration("/api/**", config);
		System.out.println("[Bean등록: CorsFilter] : 완료");
		return new CorsFilter(source);
	}	
}
