package edu.hsbremen.cloud.comparison;

import boofcv.abst.feature.associate.AssociateDescription;
import boofcv.abst.feature.associate.ScoreAssociation;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.alg.descriptor.UtilFeature;
import boofcv.factory.feature.associate.FactoryAssociation;
import boofcv.factory.feature.detdesc.FactoryDetectDescribe;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.GrayF32;
import georegression.struct.point.Point2D_F64;
import org.ddogleg.struct.FastQueue;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class SimpleComparisonStrategy implements IComparisonStrategy {


    @Override
    public Integer compare(byte[] imageReference, byte[] imageCompared) {
        try {
            Class imageType = GrayF32.class;
            DetectDescribePoint detDesc = FactoryDetectDescribe.
                    surfStable(new ConfigFastHessian(1, 2, 300, 1, 9, 4, 4), null,null, imageType);
    //				sift(new ConfigCompleteSift(0,5,600));

            ScoreAssociation scorer = FactoryAssociation.defaultScore(detDesc.getDescriptionType());
            AssociateDescription associate = FactoryAssociation.greedy(scorer, Double.MAX_VALUE, true);

            GrayF32 imageA = ConvertBufferedImage.convertFrom(ImageIO.read(new ByteArrayInputStream(imageReference)), (GrayF32) null);
            GrayF32 imageB = ConvertBufferedImage.convertFrom(ImageIO.read(new ByteArrayInputStream(imageCompared)), (GrayF32) null);

            // stores the location of detected interest points
            List<Point2D_F64> pointsA = new ArrayList<>();
            List<Point2D_F64> pointsB = new ArrayList<>();

            // stores the description of detected interest points
            FastQueue<TupleDesc> descA = UtilFeature.createQueue(detDesc,100);
            FastQueue<TupleDesc> descB = UtilFeature.createQueue(detDesc,100);

            // describe each image using interest points
            //describeImage(inputA,pointsA,descA);
            //describeImage(inputB,pointsB,descB);

            // Associate features between the two images
            associate.setSource(descA);
            associate.setDestination(descB);
            associate.associate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Detects features inside the two images and computes descriptions at those points.
     */
    /*private void describeImage(T input, List<Point2D_F64> points, FastQueue<TupleDesc> descs )
    {
        detDesc.detect(input);

        for( int i = 0; i < detDesc.getNumberOfFeatures(); i++ ) {
            points.add( detDesc.getLocation(i).copy() );
            descs.grow().setTo(detDesc.getDescription(i));
        }
    }*/
}