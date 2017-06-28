package edu.hsbremen.cloud.comparison.calc;

import com.google.cloud.vision.spi.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.*;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

@Component
public class VisionCalcualtor {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisionCalcualtor.class.getSimpleName());
    private ImageAnnotatorClient vision;

    public VisionCalcualtor() {
        try {
            vision = ImageAnnotatorClient.create();
        } catch (IOException e) {
            LOGGER.warn("Could not establish connection to Google Vision API", e);
        }
    }

    public List<String> calculate(byte[] imageBytes) {
        List<String> result = Lists.newArrayList();
        if (vision != null) {
            ByteString imgBytes = ByteString.copyFrom(imageBytes);

            // Builds the image annotation request
            final List<AnnotateImageRequest> requests = Lists.newArrayList();
            final Image img = Image.newBuilder().setContent(imgBytes).build();
            final Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            final AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    LOGGER.warn("Error occurred while analysing image: {}", res.getError().getMessage());
                } else {
                    result.addAll(Lists.transform(res.getLabelAnnotationsList(), new Function<EntityAnnotation, String>() {
                        @Nullable
                        @Override
                        public String apply(@Nullable EntityAnnotation entityAnnotation) {
                            return entityAnnotation != null ? entityAnnotation.getDescription() : null;
                        }
                    }));
                }
            }
        }
        return result;
    }
}