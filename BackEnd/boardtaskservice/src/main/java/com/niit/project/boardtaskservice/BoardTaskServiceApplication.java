package com.niit.project.boardtaskservice;

import com.niit.project.boardtaskservice.filter.JWTFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient

public class BoardTaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardTaskServiceApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean jwtFilterBean(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

		filterRegistrationBean.addUrlPatterns("/api/v2/*","/api/v3/*");
		filterRegistrationBean.setFilter(new JWTFilter());
		return filterRegistrationBean;
	}
   @Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("api/v1/*")
						.allowedHeaders("*")
						.allowedOrigins("*")
						.allowedMethods("*")
						.allowCredentials(true);
			}
		};
   }
}
