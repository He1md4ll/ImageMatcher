package edu.hsbremen.cloud.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/hello")
public class HelloWorldApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldApi.class);

    @RequestMapping("/")
    public String sayHello() {
        LOGGER.debug("Hello World Logger Test !!");
        return "Hello World !!";
    }

    @RequestMapping("/client")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String sayHelloToUser(Principal principal) {
        return "Hello " + principal.getName() + " !!";
    }

    @RequestMapping("/client/error")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sayHelloError() {
        return "";
    }
}