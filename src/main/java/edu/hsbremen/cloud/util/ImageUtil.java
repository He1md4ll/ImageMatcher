package edu.hsbremen.cloud.util;

import edu.hsbremen.cloud.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

public class ImageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

    public static Optional<ImageHolder> createThumbnail(@NotNull final ImageHolder imageHolder) {
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
            LOGGER.warn("Could not create thumbnail for image " +
                    imageHolder.getImageName() + "! Continuing without thumbnail.", e);
        }
        return imageHolderOptional;
    }
}