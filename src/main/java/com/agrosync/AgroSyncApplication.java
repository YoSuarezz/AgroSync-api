package com.agrosync;

import com.agrosync.application.secondaryports.entity.usuarios.TipoUsuarioEntity;
import com.agrosync.application.secondaryports.repository.TipoUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class AgroSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgroSyncApplication.class, args);
    }

    @Bean
    CommandLineRunner init(TipoUsuarioRepository tipoUsuarioRepository) {
        return args -> {
            var tipoUsuario = tipoUsuarioRepository.findAll();
            if (tipoUsuario.isEmpty()) {
                tipoUsuarioRepository.saveAll(List.of(
                        TipoUsuarioEntity.create().setNombre("Proveedor"),
                        TipoUsuarioEntity.create().setNombre("Cliente")
                ));
            }
        };
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:4200"
                        )
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
