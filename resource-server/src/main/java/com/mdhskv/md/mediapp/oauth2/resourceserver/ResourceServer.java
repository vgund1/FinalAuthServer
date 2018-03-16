package com.mdhskv.md.mediapp.oauth2.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mdhskv.md.mediapp")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ResourceServer extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	return super.configure(builder);
    }
}
