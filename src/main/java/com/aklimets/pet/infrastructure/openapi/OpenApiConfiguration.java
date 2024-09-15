package com.aklimets.pet.infrastructure.openapi;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("AKlimets API")
                .description("AKlimets pet project simple secrets manager API")
                .version("1.0")
                .contact(apiContact());
    }

    private Contact apiContact() {
        return new Contact()
                .name("Aliaksandr Klimets")
                .email("aliaksandrklimetst@gmail.com")
                .url("https://github.com/aklimets");
    }

    @Bean
    public GroupedOpenApi apiKeysApi() {
        return GroupedOpenApi.builder()
                .group("api-keys")
                .pathsToMatch("/api/v1/api-keys/**")
                .build();
    }
}
