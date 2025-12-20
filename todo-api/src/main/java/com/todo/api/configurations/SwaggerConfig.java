package com.todo.api.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI(@Value("${app.version:unknown}") String version) {
        return new OpenAPI().info(
                new Info()
                        .title("RD Capstone API")
                        .description("RD Capstone API describing endpoints")
                        .version(version)
        );
    }
}
