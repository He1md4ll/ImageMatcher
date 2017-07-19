package edu.hsbremen.cloud.comparison.calc;

import boofcv.alg.color.ColorHsv;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.alg.feature.color.GHistogramFeatureOps;
import boofcv.alg.feature.color.Histogram_F64;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.Planar;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@Component
public class HistogramCalculator implements Calculator<double[]> {

    @Cacheable("histograms")
    public double[] calculate(byte[] imageBytes) {
        try {
            Planar<GrayF32> rgb = new Planar<>(GrayF32.class,1,1,3);
            Planar<GrayF32> hsv = new Planar<>(GrayF32.class,1,1,3);

            BufferedImage bufferedImageA = ImageIO.read(new ByteArrayInputStream(imageBytes));

            rgb.reshape(bufferedImageA.getWidth(), bufferedImageA.getHeight());
            hsv.reshape(bufferedImageA.getWidth(), bufferedImageA.getHeight());

            ConvertBufferedImage.convertFrom(bufferedImageA, rgb, true);
            ColorHsv.rgbToHsv_F32(rgb, hsv);

            Planar<GrayF32> hs = hsv.partialSpectrum(0,1);

            // The number of bins is an important parameter.  Try adjusting it
            Histogram_F64 histogram = new Histogram_F64(12,12);
            histogram.setRange(0, 0, 2.0*Math.PI); // range of hue is from 0 to 2PI
            histogram.setRange(1, 0, 1.0);         // range of saturation is from 0 to 1

            // Compute the histogram
            GHistogramFeatureOps.histogram(hs,histogram);

            UtilFeature.normalizeL2(histogram); // normalize so that image size doesn't matter

            return histogram.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}