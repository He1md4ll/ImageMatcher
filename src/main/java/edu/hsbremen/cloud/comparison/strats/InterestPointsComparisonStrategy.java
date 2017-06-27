package edu.hsbremen.cloud.comparison.strats;

import boofcv.abst.feature.associate.AssociateDescription;
import boofcv.abst.feature.associate.ScoreAssociation;
import boofcv.abst.feature.detdesc.DetectDescribePoint;
import boofcv.abst.feature.detect.interest.ConfigFastHessian;
import boofcv.factory.feature.associate.FactoryAssociation;
import boofcv.factory.feature.detdesc.FactoryDetectDescribe;
import boofcv.struct.feature.TupleDesc;
import boofcv.struct.image.GrayF32;
import edu.hsbremen.cloud.comparison.calc.InterestPointCalculator;
import org.ddogleg.struct.FastQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InterestPointsComparisonStrategy implements IComparisonStrategy {

    private InterestPointCalculator interestPointCalculator;
    private DetectDescribePoint detectDescribePoint;

    @Autowired
    public InterestPointsComparisonStrategy(InterestPointCalculator interestPointCalculator) {
        this.interestPointCalculator = interestPointCalculator;
        this.detectDescribePoint = FactoryDetectDescribe.
                surfStable(new ConfigFastHessian(1, 2, 300,
                                1, 9, 4, 4),
                        null,null, GrayF32.class);
        this.interestPointCalculator.setDetectDescribePoint(detectDescribePoint);
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        ScoreAssociation scorer = FactoryAssociation.defaultScore(detectDescribePoint.getDescriptionType());
        AssociateDescription associate = FactoryAssociation.greedy(scorer, Double.MAX_VALUE, true);

        FastQueue<TupleDesc> imageReferenceDesc = interestPointCalculator.calculate(imageReference);
        FastQueue<TupleDesc> imageComparedDesc = interestPointCalculator.calculate(imageCompared);

        // Associate features between the two images
        associate.setSource(imageReferenceDesc);
        associate.setDestination(imageComparedDesc);
        associate.associate();
        return (double) associate.getMatches().getSize() / imageComparedDesc.getSize();
    }

    @Override
    public Double getScoreWeight() {
        return 0.4;
    }
}