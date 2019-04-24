package com.codingwolves.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/***
 * This class handles the configuration for the Spring Framework's internal mechanism.
 * The web page uses popular HTML frameworks like jQuery, BootStrap4, Font Awesome via
 * Maven. The SpringWebConfiguration class handles dependencies, resource managements etc.
 * @author Reubin George
 * @version 1.0
 * @since 04/19/2019
 */
@EnableWebMvc
@Configuration
@ComponentScan(value = "com.codingwolves.web")
public class SpringWebConfiguration implements WebMvcConfigurer {

    /**
     * This function lets the Spring's model-view-controller know where to find the resources
     * asked for by the JavaServer Page (jsp).
     * @param registry field where the registry information like BootStrap, property files
     *                 might be stored.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");

    }

    /**
     * This function is part of Spring's internal mechanism which lets the framework know
     * where the JSP is located.
     * @return Location of the the JavaServer page.
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}