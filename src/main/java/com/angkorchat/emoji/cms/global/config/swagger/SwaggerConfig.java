package com.angkorchat.emoji.cms.global.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    /**
     * 기본 OpenAPI 빈 등록 - 공통 schema, 보안, 서버 정보 등록
     */
    @Bean
    public OpenAPI baseOpenAPI() {
        String version = "V0.1";
        return new OpenAPI()
                .info(new Info()
                        .title("Emoji API")
                        .description("Emoji API Documentation")
                        .version(version))
                .components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)))
                .addSecurityItem(new SecurityRequirement().addList("Authorization"));
    }

    /**
     * CMS 그룹: /cms/** 경로만 포함
     */
    @Bean
    public GroupedOpenApi cmsGroup() {
        return GroupedOpenApi.builder()
                .group("Emoji CMS")
                .pathsToMatch("/cms/**")
                .build();
    }

    /**
     * STUDIO 그룹: /studio/** 경로만 포함
     */
    @Bean
    public GroupedOpenApi studioGroup() {
        return GroupedOpenApi.builder()
                .group("Emoji Studio")
                .pathsToMatch("/studio/**")
                .build();
    }

    // v1
//    private String version = "V0.1";
//    @Bean
//    public OpenAPI api() {
//        return new OpenAPI()
//                .addServersItem(new Server().url(" https://cms-api-emoji-sandbox.angkorlifes.com").description("Development server"))
//                .addServersItem(new Server().url("http://localhost:8095").description("Development server"))
//                .components(new Components()
//                        .addSecuritySchemes("Authorization", securityScheme()))
//                .info(apiInfo())
//                .addSecurityItem(new SecurityRequirement().addList("Authorization"));
//    }
//
//    private SecurityScheme securityScheme() {
//        return new SecurityScheme()
//                .name("Authorization")
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("Bearer")
//                .bearerFormat("JWT")
//                .in(SecurityScheme.In.HEADER);
//    }
//
//    private Info apiInfo() {
//        return new Info()
//                .title("angkorchat Emoji API")
//                .description("angkorchat Emoji API")
//                .version(version);
//    }
    //v2
//
//    private final String version = "V0.1";
//
//    @Bean
//    public GroupedOpenApi cmsGroup() {
//        return GroupedOpenApi.builder()
//                .group("Emoji CMS")
//                .pathsToMatch("/cms/**")
//                .addOpenApiCustomizer(commonCustomizer())
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi studioGroup() {
//        return GroupedOpenApi.builder()
//                .group("Emoji Studio")
//                .pathsToMatch("/studio/**")
//                .addOpenApiCustomizer(commonCustomizer())
//                .build();
//    }
//
//    private OpenApiCustomizer commonCustomizer() {
//        return openApi -> {
//            openApi.info(new Info()
//                    .title("AngkorChat Emoji API")
//                    .description("angkorchat Emoji API Documentation")
//                    .version(version)
//            );
//            openApi.addServersItem(new Server()
//                    .url("https://cms-api-emoji-sandbox.angkorlifes.com")
//                    .description("Development server"));
//            openApi.components(new Components()
//                    .addSecuritySchemes("Authorization", new SecurityScheme()
//                            .name("Authorization")
//                            .type(SecurityScheme.Type.HTTP)
//                            .scheme("bearer")
//                            .bearerFormat("JWT")
//                            .in(SecurityScheme.In.HEADER)));
//            openApi.addSecurityItem(new SecurityRequirement().addList("Authorization"));
//        };
//    }
}