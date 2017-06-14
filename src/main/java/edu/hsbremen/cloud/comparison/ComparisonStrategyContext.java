package edu.hsbremen.cloud.comparison;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

@Component
public class ComparisonStrategyContext {

    private IComparisonStrategy comparisonStrategy;

    public void setComparisonStrategy(IComparisonStrategy comparisonStrategy) {
        this.comparisonStrategy = comparisonStrategy;
    }

    public Double compare(byte[] imageReference, byte[] imageCompared) {
        Preconditions.checkNotNull(comparisonStrategy, "Set strategy first!");
        return comparisonStrategy.compare(imageReference, imageCompared);
    }
}