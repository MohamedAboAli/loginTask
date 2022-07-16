package com.example.logintask;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


//@EnableJpaRepositories(basePackages={"com.app.selection.engine.repository","com.app.orders.repository"})
//@ComponentScan
//(basePackages= {"com.app.orders.controller","com.app.orders.service.impl"}) 
//@EntityScan(value = "com.app.orders.model")



@EnableJpaRepositories(basePackages = "repository")  
@ComponentScan(basePackages= {"controllers","services"}) 
@EntityScan(value = "model")   
@SpringBootApplication
public class LoginTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginTaskApplication.class, args);
	}
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:4200");
            }
        };
    }
	
}
