package com.mdhskv.md.mediapp.oauth2.resourceserver.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
public class ResourceServerTokenStoreConfig {

    @Primary
    @Bean
    public ResourceServerTokenServices remoteTokenServices() {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl(
                "http://localhost:8080/auth-server-1.0-SNAPSHOT/oauth/check_token");
        tokenService.setClientId("web-client");
        tokenService.setClientSecret("web-client-secret");
        return tokenService;
    }

	   
}
