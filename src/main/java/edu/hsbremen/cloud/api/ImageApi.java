package edu.hsbremen.cloud.api;

import edu.hsbremen.cloud.dto.ImageDto;
import edu.hsbremen.cloud.facade.impl.ApiFacade;
import edu.hsbremen.cloud.persistance.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}