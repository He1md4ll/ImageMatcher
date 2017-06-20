package edu.hsbremen.cloud.comparison.strats;

import edu.hsbremen.cloud.comparison.calc.HistogramCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistogramComparisonStrategy implements IComparisonStrategy {

    private HistogramCalculator histogramCalculator;

    @Autowired
    public HistogramComparisonStrategy(HistogramCalculator histogramCalculator) {
        this.histogramCalculator = histogramCalculator;
    }

    @Override
    public Double compare(byte[] imageReference, byte[] imageCompared) {
        final double[] imageReferenceResult = histogramCalculator.calculate(imageReference);
        final double[] imageComparedResult = histogramCalculator.calculate(imageCompared);

        // TODO: Find way to compare
        return imageReferenceResult[0] / imageComparedResult[0];
    }
}