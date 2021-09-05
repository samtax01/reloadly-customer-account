package com.reloadly.customeraccount.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger spec configuration
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        var specConfig = new OpenAPI()
                .info(new Info().title("Account Management API").version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"));
        enableAuthorization(specConfig);
        return specConfig;
    }



    private void enableAuthorization(OpenAPI specConfig){
        specConfig.components(new Components()
                .addSecuritySchemes("Authorization", new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization"))
        );
    }

}
