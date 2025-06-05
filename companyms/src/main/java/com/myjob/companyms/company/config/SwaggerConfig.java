package com.myjob.companyms.company.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Company Management Service API")
                        .version("1.0.0")
                        .description("API documentation for the Company Management Service"))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Local Server"),
                        new Server().url("https://staging.company.com").description("Staging Server"),
                        new Server().url("https://api.company.com").description("Production Server")
                ))
                .tags(Arrays.asList(
                        new Tag().name("Public APIS"),
                        new Tag().name("")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes(
                        "bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT Bearer Token for authentication")
                                .name("Authorization")
                ));
    }
}
