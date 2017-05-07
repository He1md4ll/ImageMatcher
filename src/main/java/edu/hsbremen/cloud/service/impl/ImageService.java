package edu.hsbremen.cloud.service.impl;

import autovalue.shaded.com.google.common.common.collect.Lists;
import edu.hsbremen.cloud.persistance.ImageRepository;
import edu.hsbremen.cloud.persistance.domain.ImageEntity;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import edu.hsbremen.cloud.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<ImageEntity> getAllImages() {
        return Lists.newArrayList(imageRepository.findAll());
    }

    @Override
    public List<ImageEntity> getAllImagesOfUser(UserEntity userEntity) {
        return imageRepository.findByUsers(userEntity);
    }
}