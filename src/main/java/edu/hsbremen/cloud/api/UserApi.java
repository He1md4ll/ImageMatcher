package edu.hsbremen.cloud.api;

import edu.hsbremen.cloud.dto.UserDto;
import edu.hsbremen.cloud.facade.IUserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private IUserFacade userFacade;

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public UserDto createUser(@RequestHeader String token) {
        return userFacade.createUser(token);
    }
}