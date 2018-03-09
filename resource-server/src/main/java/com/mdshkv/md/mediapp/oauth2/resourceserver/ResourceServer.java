package com.mdshkv.md.mediapp.oauth2.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.mdshkv.md.mediapp.oauth2.resourceserver")
public class ResourceServer extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	return super.configure(builder);
    }
}
