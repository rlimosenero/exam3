package com.exam.car.config;

import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Demo Exam")
                        .version("1.0")
                        .description("API documentation")
                        )
                .components(new io.swagger.v3.oas.models.Components()
                        .addParameters("Content-Type", new Parameter()
                                .in("header")
                                .name("Content-Type")
                                .required(true)
                                .example("application/json")));
    }
}

