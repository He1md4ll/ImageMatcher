package edu.hsbremen.cloud.comparison;

import java.util.Random;

public class SimpleComparisonStrategy implements IComparisonStrategy {

    @Override
    public Integer compare(byte[] imageReference, byte[] imageCompared) {
        // Todo: Add implementation for strategy
        return new Random().nextInt(100);
    }
}