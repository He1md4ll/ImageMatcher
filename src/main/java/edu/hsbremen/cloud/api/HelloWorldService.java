package edu.hsbremen.cloud.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldService {

    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello World !!";
    }
}
