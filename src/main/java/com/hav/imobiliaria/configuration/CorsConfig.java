package com.hav.imobiliaria.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configura o CORS para permitir acessos de origens específicas
        registry.addMapping("/**") // Aplica a configuração para todas as rotas da API
                .allowedOrigins("http://localhost:3000") // Permite apenas a origem do frontend local (React)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Permite os métodos HTTP especificados
                .allowedHeaders("*") // Permite todos os cabeçalhos
                .allowCredentials(true); // Permite o envio de cookies de autenticação
    }

}
