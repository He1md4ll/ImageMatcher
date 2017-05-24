package edu.hsbremen.cloud.api;

import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/hello", method = RequestMethod.GET)
public class HelloWorldApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldApi.class);

    @RequestMapping("/")
    public String sayHello() {
        LOGGER.debug("Hello World Logger Test !!");
        return "Hello World !!";
    }

    @RequestMapping("/client")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String sayHelloToUser(@AuthenticationPrincipal UserEntity userEntity) {
        return "Hello " + userEntity.getUsername() + " !!";
    }

    @RequestMapping("/client/error")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sayHelloError() {
        return "";
    }
}