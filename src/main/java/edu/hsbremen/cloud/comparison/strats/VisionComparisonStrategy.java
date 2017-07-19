package edu.hsbremen.cloud.comparison.strats;

import edu.hsbremen.cloud.comparison.calc.VisionCalcualtor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VisionComparisonStrategy implements IComparisonStrategy {

    private VisionCalcualtor visionCalcualtor;

    @Autowired
    public VisionComparisonStrategy(VisionCalcualtor visionCalcualtor) {
        this.visionCalcualtor = visionCalcualtor;
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        int matches = 0;
        final List<String> tagsImageReference = visionCalcualtor.calculate(imageReference);
        final List<String> tagsImageCompared = visionCalcualtor.calculate(imageCompared);
        for (String tagImageReference : tagsImageReference) {
            if(tagsImageCompared.stream().anyMatch(s -> s.equalsIgnoreCase(tagImageReference))) {
                matches++;
            }
        }
        return (double) matches / (double) ((tagsImageReference.size() + tagsImageCompared.size()) / 2);
    }

    @Override
    public Double getScoreWeight() {
        return 0.4;
    }
}