package net.nanxu.mall.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfigurer.
 *
 * @author: pan
 **/
@Configuration
public class SwaggerConfigurer {

    @Bean
    OpenAPI openApi() {
        return new OpenAPI(SpecVersion.V30)
                .addServersItem(new Server().url("/").description("本地"))
                .addServersItem(new Server().url("/prod-api").description("线上"))
                // See https://swagger.io/docs/specification/authentication/ for more.
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .info(new Info()
                        .title("NxMallAPI文档")
                        .version("1.0.0"));
    }

}
