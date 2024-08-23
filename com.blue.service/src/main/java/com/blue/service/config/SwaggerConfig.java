package com.blue.service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "블루팀 Service API 명세서",
                description = "블루팀 Service API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicAPI(){
        return GroupedOpenApi.builder()
                .group("com.blue.service")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("JWT-Token", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                .addSecurityItem(new SecurityRequirement().addList("JWT-Token"));
    }
}