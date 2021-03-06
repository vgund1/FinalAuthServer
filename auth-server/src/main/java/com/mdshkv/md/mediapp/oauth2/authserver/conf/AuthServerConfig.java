package com.mdshkv.md.mediapp.oauth2.authserver.conf;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.mdshkv.md.mediapp.oauth2.authserver.CustomOauthException;
import com.mdshkv.md.mediapp.oauth2.authserver.EnableMediComAuthorizationServer;
import com.mdshkv.md.mediapp.oauth2.authserver.ResourceOwnerPasswordTokenGranter;
import com.mdshkv.md.mediapp.oauth2.authserver.library.MongoAuthorizationCodeServices;
import com.mdshkv.md.mediapp.oauth2.authserver.library.MongoClientDetailsService;


@Configuration
//@EnableAuthorizationServer
@EnableMediComAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private TokenStore tokenStore;
    @Autowired(required = false) private JwtAccessTokenConverter accessTokenConverter;

   
    
    @Bean
    public MongoClientDetailsService clientDetailsService() {
        return new MongoClientDetailsService();
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new MongoAuthorizationCodeServices();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
        
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices())
                .authenticationManager(authenticationManager);
    endpoints.exceptionTranslator(loggingExceptionTranslator()); 
    OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(clientDetailsService());
    //endpoints.tokenGranter(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices(), clientDetailsService(), requestFactory));
    
    List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
    tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices(), clientDetailsService(), requestFactory));
    tokenGranters.add(new RefreshTokenGranter(tokenServices(), clientDetailsService(), requestFactory));

    TokenGranter tokenGranter = new CompositeTokenGranter(tokenGranters);

    endpoints.tokenGranter(tokenGranter);
    
    
    endpoints
    // other endpoints
    .exceptionTranslator(e -> {
        if (e instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) e;

            return ResponseEntity
                    .status(oAuth2Exception.getHttpErrorCode())
                    .body(new CustomOauthException(oAuth2Exception.getMessage()));
        } else {
            throw e;
        }
    });
    
    }

    
    
 
    
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        oauthServer.allowFormAuthenticationForClients();
    }

    @Bean
    Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException{
    	Cipher cipher=Cipher.getInstance("RSA");;
    	return cipher;
    }
    
    
	
    
    @Primary
    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
       // tokenServices.setAccessTokenValiditySeconds(1000);
       // tokenServices.setRefreshTokenValiditySeconds(100000);

        List<TokenEnhancer> enhancers = new ArrayList<>();
        if (accessTokenConverter != null) {
            enhancers.add(accessTokenConverter);
        }

       /* //Some custom enhancer
        enhancers.add(new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                final Authentication userAuthentication = authentication.getUserAuthentication();

                final DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
                Set<String> existingScopes = new HashSet<>();
                existingScopes.addAll(defaultOAuth2AccessToken.getScope());
                if (userAuthentication != null) {
                    //User has logged into system
                    existingScopes.add("read-foo");
                } else {
                    //service is trying to access system
                    existingScopes.add("another-scope");
                }

                defaultOAuth2AccessToken.setScope(existingScopes);
                return defaultOAuth2AccessToken;
            }
        });*/

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(enhancers);
        tokenServices.setTokenEnhancer(enhancerChain);

        return tokenServices;
    }
    
    @Bean
    public WebResponseExceptionTranslator loggingExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                // This is the line that prints the stack trace to the log. You can customise this to format the trace etc if you like
               // e.printStackTrace();

            	System.out.println(e.toString());
                // Carry on handling the exception
                ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                OAuth2Exception excBody = responseEntity.getBody();
                return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
            }
        };
    }
}
