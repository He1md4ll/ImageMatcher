package edu.hsbremen.cloud.facade;

import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.dto.UserDto;
import edu.hsbremen.cloud.persistance.domain.UserEntity;

import java.util.List;

public interface IApiFacade {
    UserDto createUser(String token);
    List<ImageDto> getImages(UserEntity userEntity);
}