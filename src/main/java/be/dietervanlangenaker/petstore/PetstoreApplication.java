package be.dietervanlangenaker.petstore;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetstoreApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(PetstoreApplication.class, args);
    }
    @Bean
    OpenAPI openAPI() { return new OpenAPI().info(new Info().title("Petstore").version("1.0.0") .description("Acces to our pets")); }
}
