package edu.hsbremen.cloud.api;

import edu.hsbremen.cloud.dto.ComparsionDto;
import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.dto.ImageHolder;
import edu.hsbremen.cloud.exception.ImageUploadFailedException;
import edu.hsbremen.cloud.facade.IApiFacade;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/image", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_USER')")
public class ImageApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageApi.class);

    @Autowired
    private IApiFacade apiFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<ImageDto> getAllImages(@AuthenticationPrincipal UserEntity userEntity) {
        return apiFacade.getImages(userEntity);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ImageDto uploadImage(@AuthenticationPrincipal UserEntity userEntity,
                                      @RequestParam("name") String imageName, @RequestParam("url") String imageUrl) {
            return apiFacade.saveImage(imageName, imageUrl, userEntity);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ImageDto uploadImage(@AuthenticationPrincipal UserEntity userEntity,
                                @RequestParam("file") MultipartFile multipartFile) throws ImageUploadFailedException {
        try {
            final ImageHolder imageHolder = new ImageHolder(multipartFile.getOriginalFilename(), multipartFile.getBytes());
            return apiFacade.saveImage(imageHolder, userEntity);
        } catch (IOException e) {
            LOGGER.error("Could not parse image " + multipartFile.getName(), e);
            throw new ImageUploadFailedException(e);
        }
    }

    @RequestMapping(value = "/compare/{name}", method = RequestMethod.GET)
    public List<ComparsionDto> compareImage(@AuthenticationPrincipal UserEntity userEntity,
                                            @PathVariable("name") String imageName) {
        // TODO: Add support for async non-blocking call (annotation vs DifferedResult vs Callable)
        return apiFacade.compare(imageName, userEntity);
    }
}