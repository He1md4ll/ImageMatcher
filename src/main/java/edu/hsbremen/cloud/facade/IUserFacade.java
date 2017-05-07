package edu.hsbremen.cloud.facade;

import edu.hsbremen.cloud.dto.UserDto;

public interface IUserFacade {
    UserDto createUser(String token);
}