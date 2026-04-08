package com.dinno.pocket.infrastructure.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Gateway-User-Id";
        final String emailSchemeName = "Gateway-User-Email";

        return new OpenAPI()
                .info(new Info()
                        .title("Dinno Users API")
                        .version("1.0")
                        .description("API Documentation for User Management. Simulates API Gateway injection by requiring the X-User-Id header."))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName).addList(emailSchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name("X-User-Id")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("Simulate the API gateway JWT-stripping by passing your test User UUID directly here."))
                        .addSecuritySchemes(emailSchemeName, new SecurityScheme()
                                .name("X-User-Email")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("Email of the test user")));
    }
}