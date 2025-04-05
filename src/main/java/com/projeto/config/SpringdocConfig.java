package com.projeto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringdocConfig {

    @Bean
    public OpenAPI springShopOpenAPI(){
        return new OpenAPI()
        .info(new Info()
        .title("API Restful com documentação")
        .description("Exemplo de API Restful utilizando Springdoc para documentação")
        .version("v1.0")
        .license(new License().name("Apache 2.0"). url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
        .description("Link do repositório da Aplicação - APIRestful Documentation")
        .url("https://github.com/cai0Carvalho/API-Spring-Boot"));
    }
}
