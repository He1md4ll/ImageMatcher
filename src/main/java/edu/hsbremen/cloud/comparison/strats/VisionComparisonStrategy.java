package edu.hsbremen.cloud.comparison.strats;

import edu.hsbremen.cloud.comparison.calc.VisionCalcualtor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VisionComparisonStrategy implements IComparisonStrategy {

    private VisionCalcualtor visionCalcualtor;

    @Autowired
    public VisionComparisonStrategy(VisionCalcualtor visionCalcualtor) {
        this.visionCalcualtor = visionCalcualtor;
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        return null;
    }

    @Override
    public Double getScoreWeight() {
        return 0.0;
    }
}