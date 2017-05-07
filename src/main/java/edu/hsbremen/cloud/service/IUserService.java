package edu.hsbremen.cloud.service;

import edu.hsbremen.cloud.dto.RegisterUserDto;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UserEntity registerUser(final RegisterUserDto registerUserDto);
}