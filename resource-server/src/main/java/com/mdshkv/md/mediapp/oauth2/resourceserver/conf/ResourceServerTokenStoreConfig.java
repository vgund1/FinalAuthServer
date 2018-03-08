package com.mdshkv.md.mediapp.oauth2.resourceserver.conf;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
                "http://localhost:8443/auth-service/oauth/check_token");
        tokenService.setClientId("web-client");
        tokenService.setClientSecret("web-client-secret");
        return tokenService;
    }

	
    
    static {
    TrustManager[] trustAllCerts = new TrustManager[]{
    		new X509TrustManager() {
    		    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    		        return null;
    		    }
    		    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
    		    }
    		    public void checkServerTrusted(
    		        java.security.cert.X509Certificate[] certs, String authType) {
    		    }
    		}};

    		   try {
    		    SSLContext sc = SSLContext.getInstance("SSL");
    		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    		    } catch (Exception e) {
    		   
    		    }
}
   
   
}
