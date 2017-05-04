package edu.hsbremen.cloud.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class HelloWorldService {

    @RequestMapping("/")
    public String sayHello() {
        return "Hello World !!";
    }

    @RequestMapping("/client")
    public String sayHelloToUser(Principal principal) {
        return "Hello " + principal.getName() + " !!";
    }
}