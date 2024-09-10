package team9.gccoffee.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "REST API",
                version = "ver 0.1",
                description = "[team9] RESTful API Documentation" //설명 표시
        ),
        servers = { @Server(
                description = "Prod ENV",
                url = "http://localhost:8080/"
        ),
                @Server(
                        description = "Staging ENV",
                        url = "http://localhost:8080/staging"
                )
        }
)

public class SwaggerOpenAPIConfig {
}
