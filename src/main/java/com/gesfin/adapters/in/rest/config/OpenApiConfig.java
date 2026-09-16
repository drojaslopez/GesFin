package com.gesfin.adapters.in.rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gesfinOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión Financiera Familiar (GesFin)")
                        .version("1.0.0")
                        .description("""
                                API REST del sistema de gestión financiera familiar construida bajo \
                                Arquitectura Hexagonal y Domain-Driven Design (DDD).

                                **Módulos disponibles:**
                                - **Usuarios:** Registro y consulta de perfiles.
                                - **Grupos Familiares:** Creación de grupos y asignación de miembros.
                                - **Metas de Ahorro:** Creación de metas con prorrateo equitativo de cuotas.
                                - **Aportes:** Registro de aportes a metas y actualización del progreso.
                                - **Transacciones:** Registro de ingresos y gastos.

                                Cada endpoint incluye una descripción y un ejemplo de petición para su ejecución.
                                """)
                        .contact(new Contact()
                                .name("Equipo GesFin"))
                        .license(new License()
                                .name("Propietario")
                                .url("https://gesfin.com/licencia")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local (PostgreSQL vía Docker)")));
    }
}