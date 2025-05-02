package com.sales.user.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.*;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        try {
            return new OpenAPI()
                    .info(new Info()
                            .title("User Microservice API")
                            .version("v1")
                            .description("API for user registration and management")
                            .contact(new Contact().name("Support Team").email("support@example.com")));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error configuring OpenAPI", e);
        }
    }
}
