package edu.hsbremen.cloud.api;

import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.dto.ImageHolder;
import edu.hsbremen.cloud.exception.ImageUploadFailedException;
import edu.hsbremen.cloud.facade.impl.ApiFacade;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/image", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageApi {

    @Autowired
    private ApiFacade apiFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ImageDto> getAllImages(@AuthenticationPrincipal UserEntity userEntity) {
        return apiFacade.getImages(userEntity);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ImageDto uploadImage(@AuthenticationPrincipal UserEntity userEntity,
                                      @RequestParam("file") MultipartFile multipartFile) throws ImageUploadFailedException {
        try {
            final ImageHolder imageHolder = new ImageHolder(multipartFile.getName(), multipartFile.getBytes());
            return apiFacade.saveImage(imageHolder, userEntity);
        } catch (IOException e) {
            // TODO: Add logging
            throw new ImageUploadFailedException(e);
        }
    }
}