package com.umarta.dailyinfo.app.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API для работы с сервисами Центрального банка",
                version = "1.0"
        )
)
public class OpenApiConfig {

}
