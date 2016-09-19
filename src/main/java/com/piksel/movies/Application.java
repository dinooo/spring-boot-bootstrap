package com.piksel.movies;

import com.piksel.movies.config.JerseyInitialization;
import com.piksel.movies.secure.AuthenticationTokenProcessingFilter;
import com.piksel.movies.service.SecurityService;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    @Inject
    private SecurityService securityService;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyInitialization.class.getName());
        return registration;
    }

    @Bean
    public AuthenticationTokenProcessingFilter authenticationFilter() {
        return new AuthenticationTokenProcessingFilter(securityService);
    }
}
