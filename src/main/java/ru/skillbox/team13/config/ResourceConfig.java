package ru.skillbox.team13.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ResourceConfig implements WebMvcConfigurer
{

    private List<String> ClasspathResourceLocations = new ArrayList<>();

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        ClasspathResourceLocations.add("classpath:/META-INF/resources/");
        ClasspathResourceLocations.add("classpath:/resources/");
        ClasspathResourceLocations.add("classpath:/static/");
        ClasspathResourceLocations.add("classpath:/");

        registry.addResourceHandler("/**")
                .addResourceLocations(ClasspathResourceLocations.toArray(String[]::new));
    }
}
