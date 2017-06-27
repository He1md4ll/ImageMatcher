package edu.hsbremen.cloud.comparison.calc;

import boofcv.abst.scene.ImageClassifier;
import boofcv.factory.scene.ClassifierAndSource;
import boofcv.factory.scene.FactoryImageClassifier;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.Planar;
import com.google.common.collect.Lists;
import deepboof.io.DeepBoofDataBaseOps;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class CategoryCalculator {

    public List<ImageClassifier.Score> calculate(byte[] imageBytes) {
        try {
            final ClassifierAndSource cs = FactoryImageClassifier.nin_imagenet();
            final File modelFile = DeepBoofDataBaseOps.downloadModel(cs.getSource(), new File("download_data"));
            ImageClassifier<Planar<GrayF32>> classifier = cs.getClassifier();
            classifier.loadModel(modelFile);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            Planar<GrayF32> image = new Planar<>(GrayF32.class, bufferedImage.getWidth(), bufferedImage.getHeight(), 3);
            ConvertBufferedImage.convertFromMulti(bufferedImage, image, true, GrayF32.class);
            classifier.classify(image);
            return classifier.getAllResults();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }
}