package com.mdhskv.md.mediapp.oauth2.resourceserver.web;

import java.security.Principal;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mdhskv.md.mediapp.oauth2.resourceserver.IAuthenticationFacade;

@RestController
public class FooController {
	
	
	@Autowired
    private IAuthenticationFacade authenticationFacade;

    @PreAuthorize("#oauth2.hasScope('read-foo')")
    @RequestMapping(method = RequestMethod.GET, value = "/foo")
    @ResponseBody
    public Integer findById() {
        return new SecureRandom().nextInt();
    }
    
    @PreAuthorize("#oauth2.hasScope('test')")
    @RequestMapping(method = RequestMethod.GET, value = "/foo1")
    @ResponseBody
    public Integer findById1(Principal principal) {
    	System.out.println("getpinciple +"+principal.getName());
    	Authentication authentication = authenticationFacade.getAuthentication();
    	System.out.println("authenticationFacade "+authentication.getName());
    	
    	User details = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	System.out.println();
        return new SecureRandom().nextInt();
    }
}
