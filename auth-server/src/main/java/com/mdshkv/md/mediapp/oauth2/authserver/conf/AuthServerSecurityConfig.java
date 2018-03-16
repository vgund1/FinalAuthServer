package com.mdshkv.md.mediapp.oauth2.authserver.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mdshkv.md.mediapp.oauth2.authserver.DaoAuthenticationProvider;
import com.mdshkv.md.mediapp.oauth2.authserver.library.MongoUserDetailsService;

@Configuration
public class AuthServerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DaoAuthenticationProvider daoAuthenticationProvider;
    
	@Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new MongoUserDetailsService();
    }
    
    AuthServerSecurityConfig(){
    	 super(true);
    }

    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider);
   }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/login*","/getConfig*").anonymous()
        .anyRequest().authenticated().and()
    	.httpBasic().disable().authenticationProvider(daoAuthenticationProvider);
    	http.anonymous().disable();
    	}

    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/login*","/getConfig*");
    	
    }

    @Bean(name="authenticationManager")
    @Lazy
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    
    
    
    
}
