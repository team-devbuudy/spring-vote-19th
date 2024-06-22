package ceos.springvote.config;


import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Spring Vote API")
                        .description("Spring Vote API Documentation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("CEOS Team")
                                .email("support@ceos.com")
                                .url("https://ceos.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                );
    }
    // /login을 제외한 모든 경로를 포함하는 기본 API 그룹 설정
    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .pathsToExclude("/login")  // /login 경로는 제외
                .build();
    }

    // /login 경로에 대한 추가 API 그룹 설정
    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
                .group("login")
                .pathsToMatch("/login")
                .addOpenApiCustomizer(openApi -> openApi.path("/login", new io.swagger.v3.oas.models.PathItem()
                        .post(new io.swagger.v3.oas.models.Operation()
                                .summary("로그인 로직")
                                .requestBody(new RequestBody()
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema<>()
                                                                .addProperty("loginId", new Schema<String>().type("string").description("로그인 ID"))
                                                                .addProperty("password", new Schema<String>().type("string").description("비밀번호"))
                                                        )
                                                )
                                        )
                                )
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse()
                                                .description("로그인 성공 시 회원가입 시 입력한 정보들을 반환합니다")
                                                .content(new Content()
                                                        .addMediaType("application/json", new MediaType()
                                                                .schema(new Schema<>()
                                                                        .addProperty("loginId", new Schema<String>().type("string"))
                                                                        .addProperty("username", new Schema<String>().type("string"))
                                                                        .addProperty("part", new Schema<String>().type("string"))
                                                                        .addProperty("teamName", new Schema<String>().type("string"))
                                                                        .addProperty("email", new Schema<String>().type("string"))
                                                                        .addProperty("voteCount", new Schema<Integer>().type("integer"))
                                                                        .addProperty("role", new Schema<String>().type("string"))
                                                                        .addProperty("token", new Schema<String>().type("string"))
                                                                )
                                                        )
                                                )
                                        )
                                        .addApiResponse("401", new ApiResponse()
                                                .description("로그인 실패 시 에러 메시지를 반환합니다")
                                        ))
                        )
                ))
                .build();
    }


}
