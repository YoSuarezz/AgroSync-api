package com.agrosync;

import com.agrosync.application.secondaryports.entity.usuarios.TIpoUsuarioEntity;
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
            if (tipoUsuarioRepository.count() == 0) {
                tipoUsuarioRepository.saveAll(List.of(
                        TIpoUsuarioEntity.create().setId(1L).setNombre("Cliente"),
                        TIpoUsuarioEntity.create().setId(2L).setNombre("Proveedor")
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
