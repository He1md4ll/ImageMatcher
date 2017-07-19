package edu.hsbremen.cloud.comparison.strats;

import edu.hsbremen.cloud.comparison.calc.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistogramComparisonStrategy implements IComparisonStrategy {

    private static final int FACTOR = 50;
    private Calculator<double[]> calculator;

    @Autowired
    public HistogramComparisonStrategy(Calculator<double[]> calculator) {
        this.calculator = calculator;
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        final double[] imageReferenceResult = calculator.calculate(imageReference);
        final double[] imageComparedResult = calculator.calculate(imageCompared);

        return 1 - Math.min(1, compare(imageReferenceResult, imageComparedResult) * FACTOR);
    }

    @Override
    public Double getScoreWeight() {
        return 0.1;
    }

    private Double compare(double[] histReference,  double[] histCompared) {
        double distanceC = 0.0D;
        int length = Math.min(histReference.length, histCompared.length);

        for(int j = 0; j < length; ++j) {
            double d = histReference[j] - histCompared[j];
            distanceC += d * d;
        }
        return distanceC / length;
    }
}