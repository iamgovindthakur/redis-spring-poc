package com.iamgkt.redisspringpoc;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI registrationOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Swagger API Docs redis-spring-poc")
                        .description("redis-spring-poc API Description")
                        .version("1.0"));
    }

}
