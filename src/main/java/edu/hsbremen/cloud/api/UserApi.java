package edu.hsbremen.cloud.api;

import edu.hsbremen.cloud.dto.UserDto;
import edu.hsbremen.cloud.facade.IApiFacade;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    private IApiFacade userFacade;

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public UserDto createUser(@RequestParam String token) {
        return userFacade.createUser(token);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDto getCurrentUser(@AuthenticationPrincipal UserEntity userEntity) {
        return UserDto.fromUserEntity(userEntity);
    }
}