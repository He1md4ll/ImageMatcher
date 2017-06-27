package edu.hsbremen.cloud.facade.impl;

import autovalue.shaded.com.google.common.common.base.Function;
import autovalue.shaded.com.google.common.common.collect.Lists;
import com.google.common.collect.Iterables;
import com.google.firebase.auth.FirebaseToken;
import edu.hsbremen.cloud.dto.*;
import edu.hsbremen.cloud.facade.IApiFacade;
import edu.hsbremen.cloud.facade.IComparisonFacade;
import edu.hsbremen.cloud.firebase.service.IFirebaseAuthenticationService;
import edu.hsbremen.cloud.persistance.domain.ImageEntity;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import edu.hsbremen.cloud.service.IImageService;
import edu.hsbremen.cloud.service.IUserService;
import edu.hsbremen.cloud.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class ApiFacade implements IApiFacade {

    @Autowired
    private IComparisonFacade comparisonFacade;

    @Autowired
    private IUserService userService;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IFirebaseAuthenticationService firebaseAuthenticationService;

    @Transactional
    @Override
    public UserDto createUser(String token) {
        final FirebaseToken firebaseToken = firebaseAuthenticationService.verify(token);
        final UserEntity userEntity = userService.registerUser(RegisterUserDto.fromFirebaseToken(firebaseToken));
        return UserDto.fromUserEntity(userEntity);
    }

    @Override
    public List<ImageDto> getImages(UserEntity userEntity) {
        final List<ImageEntity> allImagesOfUser = imageService.getAllImagesOfUser(userEntity);
        return Lists.transform(allImagesOfUser, new Function<ImageEntity, ImageDto>() {
            @Nullable
            @Override
            public ImageDto apply(@Nullable ImageEntity imageEntity) {
                if (imageEntity != null) {
                    return ImageDto.fromImageEntity(imageEntity);
                } else {
                    return null;
                }
            }
        });
    }

    @Override
    public ImageDto saveImage(ImageHolder imageHolder, UserEntity userEntity) {
        return ImageDto.fromImageEntity(imageService.saveImage(imageHolder, userEntity));
    }

    @Override
    public ImageDto saveImage(String imageName, String imageUrl, UserEntity userEntity) {
        final ImageHolder imageHolder = new ImageHolder(imageName, ImageUtil.loadImage(imageUrl));
        return ImageDto.fromImageEntity(imageService.saveImage(imageHolder, userEntity));
    }

    @Override
    public List<ComparsionDto> compare(String imageName, UserEntity userEntity) {
        final ImageDto referneceImage = ImageDto.fromImageEntity(imageService.getImage(imageName));
        final List<ImageDto> imageDtoList = getImages(userEntity);
        Iterables.removeIf(imageDtoList, imageDto -> imageDto.getName().equals(imageName));
        return comparisonFacade.compareImages(referneceImage, imageDtoList);
    }
}