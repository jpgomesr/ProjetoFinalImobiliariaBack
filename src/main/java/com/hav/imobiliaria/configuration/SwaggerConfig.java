package com.hav.imobiliaria.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API da Imobiliária")
                        .description("API para o sistema de gerenciamento imobiliário")
                        .version("1.0.0")
                        .termsOfService("https://www.imobiliaria.com.br/termos")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .url("https://www.imobiliaria.com.br")
                                .email("contato@imobiliaria.com.br"))
                        .license(new License()
                                .name("Licença - Imobiliária")
                                .url("https://www.imobiliaria.com.br/licenca")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT",
                                new SecurityScheme()
                                        .name("JWT")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Insira o token JWT no formato: Bearer {token}")));
    }
} 