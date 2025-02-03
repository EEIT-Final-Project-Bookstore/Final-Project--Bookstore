package com.finalproject.ispan;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringbootConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("http://localhost:5173") // 允許的前端 URL
				.allowCredentials(true) // 如果需要傳遞憑據(如 Cookie)
				.allowedMethods("GET", "POST", "PUT", "DELETE");
	}
}
