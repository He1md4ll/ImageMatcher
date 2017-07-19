package edu.hsbremen.cloud.comparison.strats;

import edu.hsbremen.cloud.comparison.calc.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VisionComparisonStrategy implements IComparisonStrategy {

    private Calculator<List<String>> calculator;

    @Autowired
    public VisionComparisonStrategy(Calculator<List<String>> calculator) {
        this.calculator = calculator;
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        int matches = 0;
        final List<String> tagsImageReference = calculator.calculate(imageReference);
        final List<String> tagsImageCompared = calculator.calculate(imageCompared);
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