package com.sns.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi jbeApi() {
    return GroupedOpenApi.builder()
        .group("jbe-api")
        .pathsToMatch("/**")
        .build();
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Spring Boot SNS Instagram")
            .description("Spring Boot SNS Instagram 프로젝트입니다.")
            .version("v0.0.1"));
  }
}