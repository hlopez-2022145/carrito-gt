package com.carritogt.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//configuracion para permitir peticiones del frontend hacia nuestra API
@Configuration
public class CorsConfig {

    //Configura CORS para todos los endpoints de la API
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // Permite las peticiones hacia las rutas que comienzan con /api/
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("*")
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS"
                        )
                        .allowedHeaders("*");
            }
        };
    }
}