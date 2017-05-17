package edu.hsbremen.cloud.service.impl;

import autovalue.shaded.com.google.common.common.collect.Lists;
import edu.hsbremen.cloud.dto.ImageHolder;
import edu.hsbremen.cloud.persistance.IBlobStorage;
import edu.hsbremen.cloud.persistance.ImageRepository;
import edu.hsbremen.cloud.persistance.domain.ImageEntity;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import edu.hsbremen.cloud.service.IImageService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public List<ImageEntity> getAllImagesOfUser(UserEntity userEntity) {
        return imageRepository.findByUser(userEntity);
    }

    @Override
    public ImageEntity saveImage(ImageHolder imageHolder, UserEntity userEntity) {
        Optional<String> thumbnailURLOptional = Optional.empty();
        final String imageURL = blobStorage.saveImage(imageHolder);
        final Optional<ImageHolder> optional = createThumbnail(imageHolder);
        if(optional.isPresent()) {
            thumbnailURLOptional = Optional.of(blobStorage.saveImage(optional.get()));
        }

        final ImageEntity imageEntity = new ImageEntity();
        imageEntity.setName(imageHolder.getImageName());
        imageEntity.setUser(userEntity);
        imageEntity.setUrl(imageURL);
        imageEntity.setThumbnailUrl(thumbnailURLOptional.orElse(null));
        imageRepository.save(imageEntity);
        return imageEntity;
    }

    private Optional<ImageHolder> createThumbnail(@NotNull final ImageHolder imageHolder) {
        Optional<ImageHolder> imageHolderOptional = Optional.empty();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(new ByteArrayInputStream(imageHolder.getImageBytes()))
                    .size(160, 160)
                    .outputQuality(0.5)
                    .toOutputStream(outputStream);
            imageHolderOptional = Optional.of(new ImageHolder("thumbnail-"
                    + imageHolder.getImageName(), outputStream.toByteArray()));
        } catch (IOException e) {
            //TODO: Add Logging
            e.printStackTrace();
        }
        return imageHolderOptional;
    }
}