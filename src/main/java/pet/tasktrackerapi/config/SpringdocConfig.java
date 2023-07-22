package pet.tasktrackerapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SpringdocConfig {

    // http://localhost:8080/swagger-ui/index.html#/
    private static final String DOC_TITLE = "Task Tracker API";
    private static final String DOC_VERSION = "1.0.0";
    private static final String DOC_DESCRIPTION = "Task Tracker API for managing tasks and users";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(DOC_TITLE)
                        .version(DOC_VERSION)
                        .description(DOC_DESCRIPTION));
    }
}
