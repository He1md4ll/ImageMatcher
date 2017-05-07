package edu.hsbremen.cloud.facade.impl;

import com.google.firebase.auth.FirebaseToken;
import edu.hsbremen.cloud.dto.RegisterUserDto;
import edu.hsbremen.cloud.dto.UserDto;
import edu.hsbremen.cloud.facade.IUserFacade;
import edu.hsbremen.cloud.firebase.service.IFirebaseAuthenticationService;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import edu.hsbremen.cloud.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserFacade implements IUserFacade {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFirebaseAuthenticationService firebaseAuthenticationService;

    @Transactional
    @Override
    public UserDto createUser(String token) {
        final FirebaseToken firebaseToken = firebaseAuthenticationService.verify(token);
        final UserEntity userEntity = userService.registerUser(RegisterUserDto.fromFirebaseToken(firebaseToken));
        return UserDto.fromUserEntity(userEntity);
    }
}