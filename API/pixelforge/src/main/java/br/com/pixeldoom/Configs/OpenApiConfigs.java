package br.com.pixeldoom.Configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfigs {
    @Bean //diz para o spring que esse é um objeto a ser gerenciado por um spring
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("PixelForge")
                        .description("This api is intended for managing the content of a pixel arts website.")
                        .termsOfService("")
                        .license(new License().name("MIT License "))
                        .version("V1")
                        .contact(new Contact())
                );
    }
}
