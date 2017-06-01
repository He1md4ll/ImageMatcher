package edu.hsbremen.cloud.service.impl;

import autovalue.shaded.com.google.common.common.collect.Lists;
import edu.hsbremen.cloud.dto.BlobDto;
import edu.hsbremen.cloud.dto.ImageHolder;
import edu.hsbremen.cloud.persistance.IBlobStorage;
import edu.hsbremen.cloud.persistance.ImageRepository;
import edu.hsbremen.cloud.persistance.domain.ImageEntity;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import edu.hsbremen.cloud.service.IImageService;
import edu.hsbremen.cloud.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private IBlobStorage blobStorage;

    @Override
    public List<ImageEntity> getAllImages() {
        return Lists.newArrayList(imageRepository.findAll());
    }

    @Override
    public ImageEntity getImage(String imageName) {
        return imageRepository.findByName(imageName);
    }

    @Override
    public List<ImageEntity> getAllImagesOfUser(UserEntity userEntity) {
        return imageRepository.findByUser(userEntity);
    }

    @Override
    public ImageEntity saveImage(ImageHolder imageHolder, UserEntity userEntity) {
        Optional<BlobDto> thumbnailBlobOptional = Optional.empty();
        final BlobDto imageBlob = blobStorage.saveImage(imageHolder);
        final Optional<ImageHolder> optional = ImageUtil.createThumbnail(imageHolder);
        if(optional.isPresent()) {
            thumbnailBlobOptional = Optional.of(blobStorage.saveImage(optional.get()));
        }

        final ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(imageHolder.getImageName());
        imageEntity.setUser(userEntity);
        imageEntity.setImageBlob(imageBlob);
        imageEntity.setThumbnailBlob(thumbnailBlobOptional.orElse(null));
        imageRepository.save(imageEntity);
        return imageEntity;
    }
}