package edu.hsbremen.cloud.service.impl;

import autovalue.shaded.com.google.common.common.collect.Lists;
import edu.hsbremen.cloud.config.SecurityConfig;
import edu.hsbremen.cloud.dto.RegisterUserDto;
import edu.hsbremen.cloud.persistance.UserRepository;
import edu.hsbremen.cloud.persistance.domain.RoleEntity;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import edu.hsbremen.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service(UserServiceImpl.NAME)
public class UserServiceImpl implements UserService {

    public static final String NAME = "UserService";
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity registerUser(RegisterUserDto registerUserDto) {
        UserEntity userEntity = findUser(registerUserDto.getUsername());
        if (userEntity == null) {
            userEntity = UserEntity.fromRegisterUserDto(registerUserDto);
            userEntity.setAuthorities(Lists.newArrayList(RoleEntity.fromSecurityRole(SecurityConfig.Roles.ROLE_USER)));
            userRepository.save(userEntity);
        }
        return userEntity;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findUser(username);
    }

    private UserEntity findUser(String username) {
        return userRepository.findByUsername(username);
    }

    @PostConstruct
    public void init() {
        // TODO: Remove add user
        if (findUser("admin") == null) {
            UserEntity adminEntity = UserEntity.fromRegisterUserDto(new RegisterUserDto("admin", "admin", "admin@admin.com"));
            adminEntity.setAuthorities(Lists.newArrayList(RoleEntity.fromSecurityRole(SecurityConfig.Roles.ROLE_USER)));
            userRepository.save(adminEntity);
        }
    }
}