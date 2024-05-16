package com.niit.project.apigateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration

public class Config {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p->p.path("/api/v1/*").uri("lb://userauthentication"))
                .route(p->p.path("/api/v4/*").uri("lb://mailService"))
                .route(p->p.path("/api/v2/**", "/api/v3/**").uri("lb://boardtaskservice"))
                .build();
    }

}
