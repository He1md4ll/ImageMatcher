package edu.hsbremen.cloud.service;

import edu.hsbremen.cloud.persistance.domain.ImageEntity;
import edu.hsbremen.cloud.persistance.domain.UserEntity;

import java.util.List;

public interface IImageService {
    List<ImageEntity> getAllImages();
    List<ImageEntity> getAllImagesOfUser(UserEntity userEntity);
}
