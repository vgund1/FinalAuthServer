package com.mdshkv.md.mediapp.oauth2.authserver;

import com.google.common.collect.Sets;
import com.mdshkv.md.mediapp.oauth2.authserver.library.document.MongoAccessToken;
import com.mdshkv.md.mediapp.oauth2.authserver.library.document.MongoAuthorizationCode;
import com.mdshkv.md.mediapp.oauth2.authserver.library.document.MongoClientDetails;
import com.mdshkv.md.mediapp.oauth2.authserver.library.document.MongoUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.authority.AuthorityUtils;

@SpringBootApplication
@ComponentScan("com.mdshkv.md.mediapp.oauth2.authserver")
public class AuthServer {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(AuthServer.class, args);

        
    }
}
