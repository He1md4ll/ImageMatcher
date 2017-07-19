package edu.hsbremen.cloud.comparison.calc;

import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.GrayF32;
import com.google.common.base.Preconditions;
import georegression.struct.point.Point2D_F64;
import org.ddogleg.struct.FastQueue;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class InterestPointCalculator implements Calculator<FastQueue<TupleDesc>> {

    private DetectDescribePoint detectDescribePoint;

    public void setDetectDescribePoint(DetectDescribePoint detectDescribePoint) {
        this.detectDescribePoint = detectDescribePoint;
    }

    @Cacheable("interestPoints")
    public FastQueue<TupleDesc> calculate(byte[] imageBytes) {
        Preconditions.checkNotNull(detectDescribePoint);
        try {
            BufferedImage bufferedImageA = ImageIO.read(new ByteArrayInputStream(imageBytes));
            GrayF32 imageA = ConvertBufferedImage.convertFrom(bufferedImageA, (GrayF32) null);

            // stores the location of detected interest points
            List<Point2D_F64> pointsA = new ArrayList<>();

            // stores the description of detected interest points
            FastQueue<TupleDesc> descA = UtilFeature.createQueue(detectDescribePoint,100);

            // describe each image using interest points
            describeImage(imageA,pointsA,descA);
            return descA;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Detects features inside the two images and computes descriptions at those points.
     */
    private void describeImage(GrayF32 input, List<Point2D_F64> points, FastQueue<TupleDesc> descs ) {
        detectDescribePoint.detect(input);
        for( int i = 0; i < detectDescribePoint.getNumberOfFeatures(); i++ ) {
            points.add( detectDescribePoint.getLocation(i).copy() );
            descs.grow().setTo(detectDescribePoint.getDescription(i));
        }
    }
}