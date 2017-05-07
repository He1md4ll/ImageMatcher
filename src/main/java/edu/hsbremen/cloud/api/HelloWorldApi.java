package edu.hsbremen.cloud.api;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/hello")
public class HelloWorldApi {

    @RequestMapping("/")
    public String sayHello() {
        return "Hello World !!";
    }

    @RequestMapping("/client")
    @PostAuthorize("hasRole('ROLE_USER')")
    public String sayHelloToUser(Principal principal) {
        return "Hello " + principal.getName() + " !!";
    }

    @RequestMapping("/client/error")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sayHelloError() {
        return "";
    }
}