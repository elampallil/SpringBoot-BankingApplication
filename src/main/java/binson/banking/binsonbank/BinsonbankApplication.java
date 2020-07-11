package binson.banking.binsonbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BinsonbankApplication {

    public static void main(String[] args) {
        SpringApplication.run(BinsonbankApplication.class, args);
        System.out.println("welcome");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                        .allowedOrigins("*");
            }
        };
    }

}
