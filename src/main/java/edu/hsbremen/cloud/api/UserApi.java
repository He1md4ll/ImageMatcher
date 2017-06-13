package edu.hsbremen.cloud.api;

import edu.hsbremen.cloud.dto.UserDto;
import edu.hsbremen.cloud.facade.IApiFacade;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private IApiFacade userFacade;

    @CrossOrigin(origins="*")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public UserDto createUser(@RequestParam String token) {
        return userFacade.createUser(token);
    }

    @CrossOrigin(origins="*")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDto getCurrentUser(@AuthenticationPrincipal UserEntity userEntity) {
        return UserDto.fromUserEntity(userEntity);
    }
}