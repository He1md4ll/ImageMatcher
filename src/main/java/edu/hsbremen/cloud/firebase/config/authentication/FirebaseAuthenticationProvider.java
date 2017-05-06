package edu.hsbremen.cloud.firebase.config.authentication;

import edu.hsbremen.cloud.exception.FirebaseUserInvalidException;
import edu.hsbremen.cloud.service.UserService;
import edu.hsbremen.cloud.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier(UserServiceImpl.NAME)
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (supports(authentication.getClass())) {
            final UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
            if (userDetails == null) {
                throw new FirebaseUserInvalidException();
            } else {
                return new FirebaseAuthenticationToken(userDetails, userDetails.getAuthorities());
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}