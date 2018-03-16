package com.mdhskv.md.mediapp.oauth2.resourceserver.web;

import java.security.SecureRandom;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @PreAuthorize("#oauth2.hasScope('read-foo')")
    @RequestMapping(method = RequestMethod.GET, value = "/foo")
    @ResponseBody
    public Integer findById() {
        return new SecureRandom().nextInt();
    }
}
