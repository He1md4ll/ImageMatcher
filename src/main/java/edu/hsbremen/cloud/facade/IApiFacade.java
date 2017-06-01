package edu.hsbremen.cloud.facade;

import edu.hsbremen.cloud.dto.ComparsionDto;
import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.dto.ImageHolder;
import edu.hsbremen.cloud.dto.UserDto;
import edu.hsbremen.cloud.persistance.domain.UserEntity;

import java.util.List;

public interface IApiFacade {
    UserDto createUser(String token);
    List<ImageDto> getImages(UserEntity userEntity);
    ImageDto saveImage(ImageHolder imageHolder, UserEntity userEntity);
    ImageDto saveImage(String imageName, String imageUrl, UserEntity userEntity);
    List<ComparsionDto> compare(String imageName, UserEntity userEntity);
}